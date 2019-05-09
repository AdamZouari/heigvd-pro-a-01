package utils;

import java.io.FileNotFoundException;
import java.util.Iterator;

import com.mysql.cj.log.NullLogger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import static org.json.simple.JSONValue.parse;

public class JsonParserCFF {

    /**
     * For each connection we parse the timestamp of arrival time and the "quai" where the train starts
     *
     * **/
    public static String parseCFF(String cffResponse) throws ParseException {
        // parsing file "JSONExample.json"
        StringBuilder result = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(cffResponse);

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

            System.out.println("Connection n° " + (i++) + ":");
            JSONObject nthConnection = connectionIt.next();

            // we get the departure connection
            JSONObject from = (JSONObject) nthConnection.get("from");

            // we get the destination connection
            JSONObject to = (JSONObject) nthConnection.get("to");

            // we have the departureTimestamp
            result.append("Departure Time" + from.get("departureTimestamp"));
            // get departure timestamps
            result.append("Quai depart n° " + from.get("platform"));

            result.append("Arrival Time" + to.get("arrivalTimestamp"));


            result.append("Quai arrival n° " + to.get("platform"));


            result.append("Duration" + nthConnection.get("duration"));
            result.append("\n");
        }
        return result.toString();
    }
}
