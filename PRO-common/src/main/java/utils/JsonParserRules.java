package utils;

import java.io.IOException;

import entities.CffRule;
import org.json.*;
import org.json.simple.parser.*;


public class JsonParserRules {


    public static JSONObject createCffRuleJson( String from, String to, String departureTime, String arrivalTime,
                                               boolean telegramNotif, boolean menuNotif, boolean disruptionNotif) {

        JSONObject newRule = new JSONObject();

        newRule.put("id",1); // TODO Comment generer et gerer les id
        newRule.put("tag","CFF");

        // take the actual dateTime, so once the rule is created
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


    public static CffRule parseCffRuleFromServer(String storedJsonAsString) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(storedJsonAsString);

        int id = (int  ) json.get("id");
        String tag = (String) json.get("tag");
        String date_debut = (String) json.get("date_debut");
        String from = (String) json.get("from");
        String to = (String) json.get("to");
        String departureTime = (String) json.get("departureTime");
        String arrivalTime = (String) json.get("arrivalTime");
        boolean telegramNotif = (boolean) json.get("telegramNotif");
        boolean menuNotif = (boolean) json.get("menuNotif");
        boolean disruptionNotif = (boolean) json.get("disruptionNotif");

        return new CffRule(id,date_debut,from,to,departureTime,arrivalTime,telegramNotif,menuNotif,disruptionNotif);

    }

    /**
     *
     *
     * **/
    public String parseRule() throws IOException, ParseException {
        // parsing file "rules.json"
        JSONParser parser = new JSONParser();

        //JSONObject json = (JSONObject) parser.parse());

        //
        // TODO id,tag(meteo,twitter,rts,cff), date de debut (des que creation), date de fin(ou durée d'execution,
        // TODO périodicité de 24 heures pour tous ?)

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

        return null;
    }

    /**
     * Create a new rule
     *
     **/

    public void createRule(int id,String tag){

        JSONObject newRule = new JSONObject();

        newRule.put("id",id);
        newRule.put("tag",tag);

        // take the actual dateTime, so once the rule is created
        newRule.put("date_debut",System.currentTimeMillis());

        newRule.put("parametres",new JSONObject());

    }
    /**
     * Add a new rule to all rules for a client
     *
     * the user should have a json containing a json array of "rules"
     * **/
    public void addJsonRuleToJsonRules(JSONObject allRules, JSONObject newRule){

        JSONArray rules = (JSONArray) allRules.get("rules");

        rules.put(newRule);
    }

    // TODO fetch json from database and
    public String jsonToRule(){
        //return new Entities.CffRule();
        return null;
    }

}
