package utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class JsonParserRules {

    public static int PERIODICITY = 24;

    public void parseRule() throws IOException, ParseException {
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

        // the periodicity is fixed to each day, so 24 hours
        newRule.put("periodicite",PERIODICITY);

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

}
