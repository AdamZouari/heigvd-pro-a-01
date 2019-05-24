package entities;

import database.DatabaseController;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ServiceTwitter;
import utils.TelegramNotification;

import java.util.ArrayList;

public class TwitterRule extends Rule {

    private static final String TAG = "TWITTER";
    private String twitterId;

    /**
     * @param id
     * @param startDate
     **/
    public TwitterRule(int id,String username, String startDate, String twitterId, boolean menuNotif,boolean telegramNotif) {
        super(id, username, TAG, startDate, menuNotif, telegramNotif);
        this.twitterId= twitterId;
    }

    @Override
    public int getPeriod() {
        return 1;
    }

    @Override
    public int getInitialDelay() {
        return 0;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public String execute() {

        JSONObject json = new JSONObject();
        int i = 1;
        JSONArray tweetLists = new JSONArray();
        ServiceTwitter service = new ServiceTwitter();
        ArrayList<String> tweets = service.getLast10Tweets(twitterId);
        StringBuilder result = new StringBuilder();

        // If the app don't find tweets
        if (tweets == null || tweets.isEmpty()) {
            String error = "Tweets aren't available ! Maybe you wrote the wrong username  or the targeted account is protected";
            return error;
        }

        json.put("user",twitterId);
        result.append("Voici les 10 derniers tweets de l'utilisateur :" + twitterId + "\n");

        // On place chaque tweet du tableau dans le json
        for (String tweet : tweets) {
            JSONObject tweetJson = new JSONObject();
            tweetJson.put("tweet",tweet);
            result.append("Tweet n°"+ i++ +": \n");
            result.append(tweet + "\n");
            tweetLists.put(tweetJson);
        }
        json.put("tweets",tweetLists);

        // Envoyer notif Telegram
        if (telegramNotif) {
            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());
            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId, telegram.encodeMessageForURL(result.toString()));
        }

        return result.toString();
    }
    public static String getTAG() {
        return TAG;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public boolean isMenuNotif() {
        return menuNotif;
    }

    public boolean isTelegramnotif() {
        return telegramNotif;
    }


}
