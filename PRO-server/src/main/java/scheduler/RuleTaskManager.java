package scheduler;


import entities.Rule;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RuleTaskManager {

    private static RuleTaskManager rtm;
    final static Logger LOG = Logger.getLogger(RuleTaskManager.class.getName());

    private boolean running;

    private ScheduledThreadPoolExecutor executor;
    private Map<String, Map<RuleTask, ScheduledFuture<?>>> taskMap;

    public static RuleTaskManager getInstance() {
        if (rtm == null) {
            rtm = new RuleTaskManager();
        }
        return rtm;
    }

    private RuleTaskManager() {
        running = false;
        taskMap = new HashMap<>();
    }

    public void loadRules(Map<String, List<Rule>> ruleLists) {
        if (!running) {
            for (String user : ruleLists.keySet()) {
                Map<RuleTask, ScheduledFuture<?>> userRulesMap = new HashMap<>();
                for (Rule r : ruleLists.get(user)) {
                    userRulesMap.put(new RuleTask(r), null);
                }
                taskMap.put(user, userRulesMap);
            }
        }
    }

    public void startScheduling() {
        if (!running) {
            executor = new ScheduledThreadPoolExecutor(1);
            executor.setRemoveOnCancelPolicy(true);
            running = true;
            for (Map<RuleTask, ScheduledFuture<?>> ruleMap : taskMap.values()) {
                for (RuleTask task : ruleMap.keySet()) {
                    ruleMap.replace(task, schedule(task));
                }
            }
        }
    }

    public void addRule(String username, RuleTask task) {
        if (taskMap.containsKey(username)) {
            taskMap.get(username).put(task, schedule(task));
        } else {
            Map<RuleTask, ScheduledFuture<?>> map = new HashMap<>();
            map.put(task, schedule(task));
            taskMap.put(username, map);
        }
    }

    public String getUserTasksResults(String username) {

        JSONObject json = new JSONObject();
        if(taskMap.containsKey(username)) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = taskMap.get(username);

            json.put("CFF", getResultsByTag(userRulesMap.keySet(), "CFF"));
            json.put("METEO", getResultsByTag(userRulesMap.keySet(), "METEO"));
            json.put("TWITTER", getResultsByTag(userRulesMap.keySet(), "TWITTER"));
            // json.put("RTS", getResultsByTag(userRulesMap.keySet(), "RTS"));
        }

        return json.toString();
    }

    public void deleteRule(String username, int ruleId) {
        if (taskMap.containsKey(username)) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = taskMap.get(username);
            for (RuleTask ruleTask : userRulesMap.keySet()) {
                if (ruleTask.getRuleID() == ruleId) {
                    userRulesMap.get(ruleTask).cancel(true);
                    userRulesMap.remove(ruleTask);
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    private ScheduledFuture<?> schedule(RuleTask task) {
        return executor.scheduleAtFixedRate(
                task,
                task.getInitialDelay(),
                task.getPeriod(),
                TimeUnit.MINUTES);
    }

    private String getResultsByTag(Set<RuleTask> ruleTasks, String tag) {
        StringBuilder sb = new StringBuilder();
        String result;

        for (RuleTask ruleTask : ruleTasks) {
            if(ruleTask.getRuleTag() == tag) {
                result = ruleTask.getRuleResult();
                if(result != null && !result.isEmpty()) {
                    sb.append(result);
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}
