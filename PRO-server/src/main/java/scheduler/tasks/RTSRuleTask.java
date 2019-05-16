package scheduler.tasks;

import database.entities.RtsRule;

public class RTSRuleTask extends RuleTask {

    public RTSRuleTask(RtsRule rule) {
        super(rule);
    }

    @Override
    public void run() {
        // TODO do the action of the RTS rule
    }
}
