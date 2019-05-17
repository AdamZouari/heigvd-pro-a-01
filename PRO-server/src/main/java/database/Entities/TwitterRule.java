package database.Entities;

import java.util.Date;

public class TwitterRule extends Rule {

    private static final String TAG = "TWITTER";
    private String twitterId,pin;
    private boolean menuNotif,telegramnotif;
    /**
     * @param id
     * @param startDate
     **/
    public TwitterRule(int id, Date startDate,String twitterId,String pin, boolean menuNotif,boolean telegramNotif) {
        super(id,TAG, startDate,twitterId,pin,menuNotif,telegramNotif);

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
