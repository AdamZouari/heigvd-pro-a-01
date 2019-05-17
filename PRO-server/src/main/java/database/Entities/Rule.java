package database.Entities;

import java.util.Date;

public abstract class Rule {

    private String id,startDate,periodicity,content;

    /**
     *
     * {
     *   Id :  int,
     *   tag = string,
     *   date_debut = string,
     *   periodicite = string,
     *   paramètres = {  }
     * }
     *
     * **/
    public Rule(int id, Date startDate, String content){
        this.id=id;
        this.startDate=startDate.;
        this.periodicity=periodicity;
        this.content=content;

    }

    abstract void formatRuleToSendServer();
}
