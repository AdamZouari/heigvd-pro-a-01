package scheduler.tasks;

import database.entities.MeteoRule;

public class MeteoRuleTask extends RuleTask {

    public MeteoRuleTask(MeteoRule rule) {
        super(rule);
    }

    @Override
    public void run() {
        // TODO do the action of the Meteo rule
    }
}
