package database.Entities;

public class CffRule extends Rule{
    private String tag;
    private boolean telegramNotif;
    private boolean disruptionNotif;

    public CffRule(String id, String tag, String startDate, String periodicity, String content,boolean telegramNotif,
                   boolean disruptionNotif) {
        super(id, startDate, periodicity, content);
        this.tag= "cff";
        this.telegramNotif = telegramNotif;
        this.disruptionNotif = disruptionNotif;
    }

    @Override
    void formatRuleToSendServer() {

    }
}
