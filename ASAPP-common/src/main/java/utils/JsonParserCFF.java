package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;



public class JsonParserCFF {

    /**
     * For each connection we parse the timestamp of arrival time and the "quai" where the train starts
     *
     * **/
    public static String parseCFF(String cffResponse,String arg1,String arg2) {
        // parsing file "JSONExample.json"
        StringBuilder result = new StringBuilder();
        JSONObject json = new JSONObject(cffResponse);

        // getting connections
        JSONArray connections = ((JSONArray)json.get("connections"));

        // iterating on connections
        Iterator<Object> connectionIt = connections.iterator();

        // In each connection we need to get:
        // - the timestamp departure
        // - the timestamp duration
        // - the timestamps arrival
        // - the "quai"

        int i = 0;
        result.append("Here are all connections possible between " + arg1 + " and " + arg2 + ": \n");

        while (connectionIt.hasNext()) {

            System.out.println("Connection n° " + (i++) + ":");
            JSONObject nthConnection = (JSONObject) connectionIt.next();

            // we get the departure connection
            JSONObject from = (JSONObject) nthConnection.get("from");

            // we get the destination connection
            JSONObject to = (JSONObject) nthConnection.get("to");

            // we have the departureTimestamp
            result.append("Departure Time :" + from.get("departure") + "\n");
            // get departure timestamps
            result.append("Quai depart n° " + from.get("platform") + "\n");

            result.append("Arrival Time :" + to.get("arrival") + "\n");


            result.append("Quai arrival n° :" + to.get("platform") + "\n");


            result.append("Duration" + nthConnection.get("duration") + "\n");
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * This function parses the Transport API request of train connections between to cities
     * and verifies for each sub-connection if there is the train has a delay
     *
     * **/
    public static String parseCFForDelay(String cffResponse,String arg1,String arg2) {

        StringBuilder result = new StringBuilder();
        JSONObject json = new JSONObject (cffResponse);

        // getting connections
        JSONArray connections = ((JSONArray)json.get("connections"));

        // iterating on connections
        Iterator<Object> connectionIt = connections.iterator();

        int i = 0;
        int totalDelay = 0;
        result.append("Here are all connections possible between " + arg1 + " and " + arg2 + ": \n");

        while (connectionIt.hasNext()) {

            System.out.println("Connection n° " + (i++) + ":");
            JSONObject nthConnection = (JSONObject) connectionIt.next();

            // we get the departure connection
            JSONObject from = (JSONObject) nthConnection.get("from");

            // we get the destination connection
            JSONObject to = (JSONObject) nthConnection.get("to");

            // we get the delay if the train is retarded
            String delay = (String) from.get("delay");

            // Here we check for each connection if there is a delay and add it to the total delay
            if(delay != null){
                totalDelay += Integer.getInteger(delay);
            }

            result.append("Delay to consider : " + delay);

            // we have the departureTimestamp
            result.append("Departure Time :" + from.get("departure") + "\n");
            // get departure timestamps2
            result.append("Quai depart n° " + from.get("platform") + "\n");

            result.append("Arrival Time :" + to.get("arrival") + "\n");


            result.append("Quai arrival n° :" + to.get("platform") + "\n");


            result.append("Duration" + nthConnection.get("duration") + "\n");
            result.append("\n");
        }
        return result.toString();
    }

    public static String timestampToDate(Long ts){
        Date date= new Timestamp(ts);
        return date.toString();
    }
}
