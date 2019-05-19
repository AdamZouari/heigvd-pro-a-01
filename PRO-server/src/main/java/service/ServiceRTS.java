package service;


/*import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;*/


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;


public class ServiceRTS extends Service {

    public ServiceRTS() {
    }

    private String token;

    private String getTokenCommand = " curl -X POST \\\n" +
            "  'https://api.srgssr.ch/oauth/v1/accesstoken?grant_type=client_credentials' \\\n" +
            "  -H 'authorization: Basic UFZZUzI2ZWFQcUY5Um12TzBLRmh5U3hCUHJHdFpIbWE6TEVHNE0zMXRFUzFKMXpycw==' \\\n" +
            "  -H 'cache-control: no-cache' \\\n" +
            "  -H 'postman-token: 5b902520-5ebb-374d-e3e8-6430cd427742'\\\n" +
            "  -H 'content-length: 0'";

    //private String programCommand = "curl -X GET --header \"Content-Type: \" --header \"Authorization: Bearer 2dIztu6lI6iAtcfUCCB3VEnffmM1\" \"https://api.srgssr.ch/epg/v1/api/programs\"\n";
    private String programCommand = "curl -X GET --header \"Content-Type: \" --header \"Authorization: Bearer GgBC9SS1GxwwqJHbuszlcbzWRNaG\" \"https://api.srgssr.ch/epg/v1/api/schedules/day\"\n";

    public ArrayList<String> getProgram() {
        Process process = null;
        String jsonRes = "";


        try {
            process = Runtime.getRuntime().exec(programCommand);
            InputStream is = process.getInputStream();
            int b = 0;
            while ((b = is.read()) != -1) {
                jsonRes += ((char) b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> result = new ArrayList<>();

        try {

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonRes);
            JSONArray schedules = ((JSONArray) json.get("schedules"));

            Iterator<JSONObject> schedulesIt = schedules.iterator();

            while (schedulesIt.hasNext()) {

                JSONObject nthSchedule = schedulesIt.next();

                JSONArray broadcasts = ((JSONArray) nthSchedule.get("broadcasts"));
                Iterator<JSONObject> broadcastIt = broadcasts.iterator();

                while (broadcastIt.hasNext()) {
                    JSONObject broadcast = broadcastIt.next();
                    String channel = (String) broadcast.get("channel");
                    JSONObject vps = (JSONObject) broadcast.get("vps");
                    if (channel.contains("RTS")) {
                        result.add(channel + broadcast.get("titles").toString() + vps.get("hour") + ":" + vps.get("minute"));
                    }
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public void connect() {
        ArrayList<String> program = getProgram();
        for(String prog :program ){
            System.out.println(prog);
        }

       /* OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.srgssr.ch/epg/v1/api/programs/")
                .get()
                .addHeader("authorization", "Basic T0NmWTltZGlWT0dza3BUSkxRdEtsODd2RERvVAo=")
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void disconnect() {

    }


}

