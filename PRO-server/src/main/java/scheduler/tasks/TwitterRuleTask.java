package scheduler.tasks;

import database.entities.TwitterRule;

public class TwitterRuleTask extends RuleTask {

    public TwitterRuleTask(TwitterRule rule) {
        super(rule);
    }

    @Override
    public void run() {
        // TODO do the action of the Twitter rule
    }
}
