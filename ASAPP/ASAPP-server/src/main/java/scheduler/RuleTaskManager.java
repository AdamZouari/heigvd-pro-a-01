package scheduler;


import entities.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RuleTaskManager {

    private static RuleTaskManager rtm;
    private final static Logger LOG = Logger.getLogger(RuleTaskManager.class.getName());

    private ScheduledThreadPoolExecutor executor;
    private Map<String, Map<RuleTask, ScheduledFuture<?>>> taskMap;

    public static RuleTaskManager getInstance() {
        if (rtm == null) {
            rtm = new RuleTaskManager();
        }
        return rtm;
    }

    private RuleTaskManager() {
        taskMap = new HashMap<>();
    }

    public void loadRules(Map<String, List<Rule>> ruleLists) {
        LOG.info("Loading rules from database and starting scheduler...");
        executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true);

        for (String user : ruleLists.keySet()) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = new ConcurrentHashMap<>();
            for (Rule r : ruleLists.get(user)) {
                RuleTask ruleTask = new RuleTask(r);
                userRulesMap.put(ruleTask, schedule(ruleTask));
            }
            taskMap.put(user, userRulesMap);
        }
    }

    public void addRule(String username, RuleTask task) {
        LOG.info("Adding rule " + task.getRuleID() + "to user : " + username);
        if (taskMap.containsKey(username)) {
            taskMap.get(username).put(task, schedule(task));
        } else {
            Map<RuleTask, ScheduledFuture<?>> map = new ConcurrentHashMap<>();
            map.put(task, schedule(task));
            taskMap.put(username, map);
        }
    }

    public String getUserTasksResults(String username) {
        LOG.info("Fetching rules from user : " + username);
        StringBuilder sb = new StringBuilder();
        if(taskMap.containsKey(username)) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = taskMap.get(username);
            sb.append("CFF\n\n");
            sb.append(getResultsByTag(userRulesMap.keySet(), "CFF"));
            sb.append("\nMETEO\n\n");
            sb.append(getResultsByTag(userRulesMap.keySet(), "METEO"));
            sb.append("\nTWITTER\n\n");
            sb.append(getResultsByTag(userRulesMap.keySet(), "TWITTER"));
            sb.append("\nRTS\n\n");
            sb.append(getResultsByTag(userRulesMap.keySet(), "RTS"));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public void deleteRule(String username, int ruleId) {
        LOG.info("Deleting rule " + ruleId + " from user : " + username);
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
            if(ruleTask.getRuleTag().equals(tag)) {
                result = ruleTask.getRuleResult();
                if(result != null && !result.isEmpty()) {
                    sb.append(result);
                    sb.append("\n\n");
                }
            }
        }
        return sb.toString();
    }
}
