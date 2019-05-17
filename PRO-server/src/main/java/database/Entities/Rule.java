package database.Entities;

import java.util.Date;

import java.util.Date;

public abstract class Rule {

    private int id;
    private String tag;
    private Date startDate;
    private boolean telegramNotif, menuNotif;


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
    public Rule(int id, String tag, Date startDate, boolean menuNotif, boolean telegramNotif){

        this.id=id;
        this.tag=tag;
        this.startDate=startDate;
        this.menuNotif= menuNotif;
        this.telegramNotif = telegramNotif;
    }

    abstract void formatRuleToSendServer();
}
