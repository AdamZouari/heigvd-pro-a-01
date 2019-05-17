package database.Entities;

import java.util.Date;

public class MeteoRule extends Rule {
    private static final String TAG = "METEO";
    private String time, location, weatherType, temperature, temperatureSelection, noteText;
    /**
     *
     * @param id
     * @param tag
     * @param startDate
     * @param periodicity
     * @param content
     **/
    public MeteoRule(int id, Date startDate, boolean telegramNotif, boolean menuNotif) {
        super(id, TAG, startDate, menuNotif,telegramNotif);
        // TODO voir ce qu'on a besoin
    }

    @Override
    void formatRuleToSendServer() {

    }
}
