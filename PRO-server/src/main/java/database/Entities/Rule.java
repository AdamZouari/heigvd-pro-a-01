package database.Entities;

import java.util.Date;

import java.util.Date;

public abstract class Rule {

    private int id;
    private String tag;
    private Date startDate;


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
    public Rule(int id, String tag, Date startDate){

        this.id=id;
        this.tag=tag;
        this.startDate=startDate;
    }

    abstract void formatRuleToSendServer();
}
