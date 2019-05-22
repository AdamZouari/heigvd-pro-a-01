package scheduler;

import entities.Rule;

public class RuleTask implements Runnable {

    private Rule rule;

    private String ruleResult;

    public RuleTask(Rule rule) {
        this.rule = rule;
    }

    public String getRuleResult() {
        return ruleResult;
    }

    public int getRuleID() {
        return 0; // rule.getId();
    }

    public int getInitialDelay() {
        return 1; // rule.getInitialDelay(); // get minutes
    }

    @Override
    public void run() {
        ruleResult = rule.execute();
        System.out.println(ruleResult);
    }
}
