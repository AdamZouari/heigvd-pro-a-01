package scheduler.tasks;

import entities.Rule;

public abstract class RuleTask implements Runnable {

    private Rule rule;

    private String RuleResult;

    public RuleTask(Rule rule) {
        this.rule = rule;
    }

    public String getRuleResult() {
        return RuleResult;
    }

    public int getRuleID() {
        // TODO need rule.getId() as an int
        return 0;
    }

    public int getInitialDelay() {
        // TODO need date from rule and convert to delay from actual date
        return 1; // minutes
    }

    public int getPeriod() {
        // TODO need rule.getPeriode() as an int in minutes
        return 1440; // 60 * 24 min -> 1 day
    }

}
