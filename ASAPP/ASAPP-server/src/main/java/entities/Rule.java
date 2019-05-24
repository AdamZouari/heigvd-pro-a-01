package entities;

import org.json.JSONObject;

import java.time.ZonedDateTime;

public abstract class Rule {

    protected int id;
    protected String tag;
    protected String startDate;
    protected boolean telegramNotif, menuNotif;
    private String username;
    /**
     *
     * {
     *   Id :  int,
     *   tag = string,
     *   date_debut = Date,
     *   paramètres = {  }
     * }
     *
     * **/
    public Rule(int id,String username, String tag, String startDate, boolean menuNotif, boolean telegramNotif){

        this.id=id;
        this.tag=tag;
        this.startDate=startDate;
        this.menuNotif= menuNotif;
        this.telegramNotif = telegramNotif;
        this.username=username;

    }

    public Rule(JSONObject rule) {
        id = (int)rule.get("id");
        tag = rule.get("tag").toString();
        startDate = rule.get("date_debut").toString();
        menuNotif = (boolean)rule.get("menuNotif");
        telegramNotif = (boolean)rule.get("telegram_notif");
    }

    public String getUsername() {
        return username;
    }

    public abstract int getPeriod();

    public String getTag() {
        return tag;
    }

    public int getId() {
        return id;
    }

    public abstract int getInitialDelay();

    protected int getDelayFromRequestTime(int requestTimeMinutes) {
        // 1 day in minutes
        int dayInMin = 24 * 60;

        // current time
        ZonedDateTime now = ZonedDateTime.now();

        // delay before starting the task in the scheduler (minutes)
        return (requestTimeMinutes - ((now.getHour() * 60) + now.getMinute())+ dayInMin) % (dayInMin);
    }

    /**
     *
     * @return un JSON representant la rule
     */
    public abstract JSONObject toJSON();
    public abstract String execute();


}
