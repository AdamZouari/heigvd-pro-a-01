package scheduler;


import entities.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public String getUserTasks(String username) {

        // TODO return rules result depending on tags ordered
        // TODO and separed by new line between same tag and ----- if tags changes
        //return taskMap.get(username).keySet();
        return "";
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

    public void deleteAllRule(String username) {
        if (taskMap.containsKey(username)) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = taskMap.get(username);
            for (RuleTask ruleTask : userRulesMap.keySet()) {
                userRulesMap.get(ruleTask).cancel(true);
                userRulesMap.remove(ruleTask);
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

}
