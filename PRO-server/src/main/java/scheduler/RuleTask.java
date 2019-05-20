package scheduler;

import entities.Rule;

public class RuleTask implements Runnable {

    private Rule rule;

    private String RuleResult;

    public RuleTask(Rule rule) {
        this.rule = rule;
    }

    public String getRuleResult() {
        return RuleResult;
    }

    public int getRuleID() {
        return 0; // rule.getId();
    }

    public int getInitialDelay() {
        return 1; // rule.getInitialDelay(); // get minutes
    }

    @Override
    public void run() {
        RuleResult = null; // rule.execute();
    }
}
