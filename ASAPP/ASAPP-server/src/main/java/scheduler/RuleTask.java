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

    public int getPeriod() {
        return rule.getPeriod();
    }

    public String getRuleTag() {
        return this.rule.getTag();
    }

    public int getRuleID() {
        return rule.getId();
    }

    public int getInitialDelay() {
        return rule.getInitialDelay();
    }

    @Override
    public void run() {
        ruleResult = rule.execute();
        System.out.println(ruleResult);
    }
}
