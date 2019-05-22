package entities;

import org.json.JSONObject;

public abstract class Rule {

    protected int id;
    protected String tag;
    protected String startDate;
    protected boolean telegramNotif, menuNotif;


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
    public Rule(int id, String tag, String startDate, boolean menuNotif, boolean telegramNotif){

        this.id=id;
        this.tag=tag;
        this.startDate=startDate;
        this.menuNotif= menuNotif;
        this.telegramNotif = telegramNotif;
    }

    public Rule(JSONObject rule) {
        id = Integer.parseInt((String)rule.get("id"));
        tag = rule.get("tag").toString();
        startDate = rule.get("date_debut").toString();
        menuNotif = (boolean)rule.get("menuNotif");
        telegramNotif = (boolean)rule.get("telegram_notif");
    }

    /**
     *
     * @return un JSON representant la rule
     */
    public abstract JSONObject toJSON();
    public abstract String execute();


}
