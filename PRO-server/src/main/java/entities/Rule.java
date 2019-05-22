package entities;

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

    public abstract String execute();
}
