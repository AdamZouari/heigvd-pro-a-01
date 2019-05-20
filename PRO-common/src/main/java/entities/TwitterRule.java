package entities;

public class TwitterRule extends Rule {

    private static final String TAG = "TWITTER";
    private String twitterId,pin;
    private boolean menuNotif,telegramnotif;
    /**
     * @param id
     * @param startDate
     **/
    public TwitterRule(int id, String startDate,String twitterId,String pin, boolean menuNotif,boolean telegramNotif) {
        super(id,TAG, startDate,menuNotif,telegramNotif);
        this.twitterId= twitterId;
        this.pin=pin;

    }

    @Override
    void formatRuleToSendServer() {

    }

    public static String getTAG() {
        return TAG;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public String getPin() {
        return pin;
    }

    public boolean isMenuNotif() {
        return menuNotif;
    }

    public boolean isTelegramnotif() {
        return telegramnotif;
    }

}
