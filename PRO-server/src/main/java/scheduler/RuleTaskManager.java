package scheduler;


import entities.Rule;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RuleTaskManager {

    private static RuleTaskManager rtm;
    private int period;
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
        period = 1;//24 * 60; // 1 day in minutes
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

        StringBuilder sb = new StringBuilder();

        if(taskMap.containsKey(username)) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = taskMap.get(username);
            // TODO FIX problem with new lines
            sb.append("----- CFF");
            sb.append(getResultsByTag(userRulesMap.keySet(), "CFF"));
            sb.append("----- METEO");
            sb.append(getResultsByTag(userRulesMap.keySet(), "METEO"));
            sb.append("----- TWITTER");
            sb.append(getResultsByTag(userRulesMap.keySet(), "TWITTER"));
            /*
            sb.append("----- RTS");
            sb.append(getResultsByTag(userRulesMap.keySet(), "RTS"));
            */
        }

        System.out.println(sb.toString());
        return sb.toString();
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
                period,
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
