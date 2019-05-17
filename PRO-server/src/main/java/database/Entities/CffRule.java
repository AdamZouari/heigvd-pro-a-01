package database.Entities;

import java.util.Date;

public class CffRule extends Rule{
    private boolean telegramNotif;
    private boolean disruptionNotif;

    public CffRule(int id, String tag, Date startDate, String from, String to, String departureTime, String arrivalTime, boolean telegramNotif, boolean menuNotif,
                   boolean disruptionNotif) {
        super(id, "cff", startDate);
        this.
        this.telegramNotif = telegramNotif;
        this.disruptionNotif = disruptionNotif;
    }

    @Override
    void formatRuleToSendServer() {

    }
}
