package utils;

import org.json.*;


public class JsonParserRules {


    /**
     *
     * Creates a rule for cff, used once user adds a rule on the client side to send it to the server in order to add
     * it in the database
     * **/
    public static JSONObject createCffRuleJson( String from, String to, String departureTime, String arrivalTime,
                                               boolean telegramNotif, boolean menuNotif, boolean disruptionNotif) {

        JSONObject newRule = new JSONObject();

        newRule.put("id",(int)(System.currentTimeMillis() % Integer.MAX_VALUE));
        newRule.put("tag","CFF");

        // take the actual dateTime, so once the rule is created

        // TODO a enlever
        newRule.put("date_debut",System.currentTimeMillis());

        newRule.put("from",from);
        newRule.put("to",to);

        newRule.put("departureTime",departureTime);

        newRule.put("arrivalTime",arrivalTime);

        newRule.put("telegramNotif",telegramNotif);
        newRule.put("menuNotif",menuNotif);
        newRule.put("disruptionNotif",disruptionNotif);

        return newRule;
    }

    /**
     *
     * Creates a rule for RTS, used once user adds a rule on the client side to send it to the server in order to add
     * it in the database
     * **/
    public static JSONObject createRTSRuleJson( String channel,String requestTime, boolean menuNotif,
                                                boolean telegramNotif) {

        JSONObject newRule = new JSONObject();

        newRule.put("id",(int)(System.currentTimeMillis() % Integer.MAX_VALUE));
        newRule.put("tag","RTS");

        // take the actual dateTime, so once the rule is created
        newRule.put("date_debut",System.currentTimeMillis());

        newRule.put("channel",channel);
        newRule.put("requestTime",requestTime);

        newRule.put("menuNotif",menuNotif);
        newRule.put("telegram_notif",telegramNotif);

        return newRule;
    }
    /**
     *
     * Creates a rule for Meteo, used once user adds a rule on the client side to send it to the server in order to add
     * it in the database
     * **/
    public static JSONObject createMeteoRuleJson( boolean telegramNotif, boolean menuNotif, String time, String location,
                                                  String weatherType, String temperature, String temperatureSelection) {

        JSONObject newRule = new JSONObject();

        newRule.put("id",(int)(System.currentTimeMillis() % Integer.MAX_VALUE));
        newRule.put("tag","METEO");

        // take the actual dateTime, so once the rule is created
        newRule.put("date_debut",System.currentTimeMillis());

        newRule.put("time",time);
        newRule.put("location",location);
        newRule.put("weatherType",weatherType);
        newRule.put("temperature",temperature);
        newRule.put("temperatureSelection",temperatureSelection);
        newRule.put("menuNotif",menuNotif);
        newRule.put("telegram_notif",telegramNotif);

        return newRule;
    }

    /**
     *
     * Creates a rule for twitter, used once user adds a rule on the client side to send it to the server in order to add
     * it in the database
     * **/

    public static JSONObject createTwitterRuleJson( String twitterId,String pin, boolean menuNotif,boolean telegramNotif) {

        JSONObject newRule = new JSONObject();

        newRule.put("id",(int)(System.currentTimeMillis() % Integer.MAX_VALUE));
        newRule.put("tag","TWITTER");

        // take the actual dateTime, so once the rule is created
        newRule.put("date_debut",System.currentTimeMillis());
        newRule.put("twitterId",twitterId);

        newRule.put("pin",pin);

        newRule.put("menuNotif",menuNotif);
        newRule.put("telegram_notif",telegramNotif);

        return newRule;
    }


    /**
     * Parse rule to show it prettily in the GUI
     *
     * **/
    public static String parseCffRule(String storedJsonAsString) {

        JSONObject json = new JSONObject(storedJsonAsString);
        StringBuilder stringBuilder = new StringBuilder();

        int id = (int  ) json.get("id");
        String tag = (String) json.get("tag");
        Long date_debut = (Long) json.get("date_debut");
        String from = (String) json.get("from");
        String to = (String) json.get("to");
        String departureTime = (String) json.get("departureTime");
        String arrivalTime = (String) json.get("arrivalTime");
        boolean telegramNotif = (boolean) json.get("telegramNotif");
        boolean menuNotif = (boolean) json.get("menuNotif");
        boolean disruptionNotif = (boolean) json.get("disruptionNotif");

        return json.toString();//new CffRule(id,date_debut,from,to,departureTime,arrivalTime,telegramNotif,menuNotif,disruptionNotif);

    }

    public static String parseRTSRule(String storedJsonAsString) {

        JSONObject json = new JSONObject(storedJsonAsString);
        StringBuilder stringBuilder = new StringBuilder();

        int id = (int  ) json.get("id");
        String tag = (String) json.get("tag");
        Long date_debut = (Long) json.get("date_debut");
        String channel = (String) json.get("channel");
        String requestTime = (String) json.get("requestTime");
        boolean telegramNotif = (boolean) json.get("telegramNotif");
        boolean menuNotif = (boolean) json.get("menuNotif");

        return json.toString();
    }

    public static String parseMeteoRule(String storedJsonAsString) {

        JSONObject json = new JSONObject(storedJsonAsString);
        StringBuilder stringBuilder = new StringBuilder();

        int id = (int  ) json.get("id");
        String tag = (String) json.get("tag");
        Long date_debut = (Long) json.get("date_debut");
        String time = (String) json.get("time");
        String location = (String) json.get("location");
        String weatherType = (String) json.get("weatherType");
        String temperature = (String) json.get("temperature");
        String temperatureSelection = (String) json.get("temperatureSelection");
        String noteText = (String) json.get("noteText");
        boolean telegramNotif = (boolean) json.get("telegramNotif");
        boolean menuNotif = (boolean) json.get("menuNotif");

        return json.toString();
    }

    public static String parseTwitterRule(String storedJsonAsString) {

        JSONObject json = new JSONObject(storedJsonAsString);
        StringBuilder stringBuilder = new StringBuilder();

        int id = (int  ) json.get("id");
        String tag = (String) json.get("tag");
        Long date_debut = (Long) json.get("date_debut");
        String twitterId = (String) json.get("twitterId");
        String pin = (String) json.get("pin");
        boolean telegramNotif = (boolean) json.get("telegramNotif");
        boolean menuNotif = (boolean) json.get("menuNotif");

        return json.toString();
    }


}
