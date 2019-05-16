package database.entities;

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
    public Rule(String id,String startDate,String periodicity,String content){
        this.id=id;
        this.startDate=startDate;
        this.periodicity=periodicity;
        this.content=content;

    }
    abstract void formatRuleToSendServer();
}
