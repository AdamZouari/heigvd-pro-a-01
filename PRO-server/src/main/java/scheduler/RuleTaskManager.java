package scheduler;

import scheduler.tasks.RuleTask;

import java.util.HashMap;
import java.util.Map;
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
        if(rtm == null) {
            rtm = new RuleTaskManager();
        }
        return rtm;
    }

    private RuleTaskManager() {
        running = false;
        taskMap = new HashMap<>();
    }

    public void fetchDataBaseRules() {
        // TODO if the fetch is at a higher level, this method will be changed to addFetchedRules(Map<userId, ruleTask>)
        if(!running) {
            // TODO load taskMap with database content
        }
        // for now, can't load taskMap on running scheduler
    }

    public void startScheduling() {
        if(!running) {
            executor = new ScheduledThreadPoolExecutor(1);
            executor.setRemoveOnCancelPolicy(true);
            running = true;
            // TODO go through taskMap and start all tasks (none if empty)
        }
    }

    public void addRule(String userId, RuleTask ruleTask) {
        ScheduledFuture<?> sf = executor.scheduleAtFixedRate(
                ruleTask,
                ruleTask.getInitialDelay(),
                ruleTask.getPeriod(),
                TimeUnit.MINUTES);

        if(taskMap.containsKey(userId)) {
            taskMap.get(userId).put(ruleTask, sf);
        } else {
            Map<RuleTask, ScheduledFuture<?>> map = new HashMap<>();
            map.put(ruleTask, sf);
            taskMap.put(userId, map);
        }
    }

    public void deleteRule(String userId, int ruleId) {

        // TODO not really good but I don't know how to do it better without using the RuleTask as id
        if(taskMap.containsKey(userId)) {
            Map<RuleTask, ScheduledFuture<?>> userRulesMap = taskMap.get(userId);
            for (RuleTask ruleTask: userRulesMap.keySet()) {
                if(ruleTask.getRuleID() == ruleId) {
                    userRulesMap.get(ruleTask).cancel(true);
                    userRulesMap.remove(ruleTask);
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

}
