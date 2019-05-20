package Entities;

import java.util.Date;

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
}
