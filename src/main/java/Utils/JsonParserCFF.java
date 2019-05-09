package Utils;

import com.mysql.cj.xdevapi.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import static org.json.simple.JSONValue.parse;

public class JsonParserCFF {

    /**
     * For each connection we parse the timestamp of arrival time and the "quai" where the train starts
     *
     * **/
    public static void parseCFF(String cffResponse) throws FileNotFoundException, ParseException {
        // parsing file "JSONExample.json"

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(cffResponse);
        // getting firstName and lastName
        String firstName = (String) json.get("firstName");
        String lastName = (String) json.get("lastName");

        System.out.println(firstName);
        System.out.println(lastName);

        // getting connections
        JSONArray connections = ((JSONArray)json.get("connections"));

        // iterating on connections
        Iterator<JSONObject> connectionIt = connections.iterator();

        // In each connection we need to get:
        // - the timestamp departure
        // - the timestamp duration
        // - the timestamps arrival
        // - the "quai"

        int i = 0;
        while (connectionIt.hasNext()) {

            System.out.println("Connection n° " + i + ":");
            JSONObject nthConnection = connectionIt.next();

            // we get the departure connection
            JSONObject from = (JSONObject) nthConnection.get("from");

            // we get the destination connection
            JSONObject to = (JSONObject) nthConnection.get("to");

            // we have the departureTimestamp
            System.out.println("Departure Time" + from.get("departureTimestamp"));
            // get departure timestamps
            System.out.println("Quai depart n° " + from.get("platform"));

            System.out.println("Arrival Time" + to.get("arrivalTimestamp"));


            System.out.println("Quai arrival n° " + to.get("platform"));


            System.out.println("Duration" + nthConnection.get("duration"));
        }

    }
}
