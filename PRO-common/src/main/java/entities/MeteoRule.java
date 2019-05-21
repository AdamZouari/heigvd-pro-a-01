package entities;

public class MeteoRule extends Rule {
    private static final String TAG = "METEO";
    private String time, location, weatherType, temperature, temperatureSelection, noteText;
    /**
     *
     * @param id
     **/
    public MeteoRule(int id, String startDate, boolean telegramNotif, boolean menuNotif) {
        super(id, TAG, startDate, menuNotif,telegramNotif);
        // TODO voir ce qu'on a besoin
    }

    @Override
    void formatRuleToSendServer() {

    }

    @Override
    public void execute() {

        // TO DO
    }
}
