package scheduler.tasks;

import database.entities.CffRule;

public class CFFRuleTask extends RuleTask {

    public CFFRuleTask(CffRule rule) {
        super(rule);
    }

    @Override
    public void run() {
        // TODO do the action of the CFF rule
    }
}
