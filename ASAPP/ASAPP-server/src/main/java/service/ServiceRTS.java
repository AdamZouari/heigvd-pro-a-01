package service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceRTS extends Service {

    public ServiceRTS() {
    }

    private String accessToken;
    final private String urlToken = "https://api.srgssr.ch/oauth/v1/accesstoken?grant_type=client_credentials";

    /**
     * Set accessToken to access to the RTS API content
     */
    private void setToken() {

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String token = new String();
        String answer = new String();

        try {
            // Get Token
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, answer);
            Request request = new Request.Builder()
                    .url(urlToken)
                    .header("Authorization", "Basic UFZZUzI2ZWFQcUY5Um12TzBLRmh5U3hCUHJHdFpIbWE6TEVHNE0zMXRFUzFKMXpycw==")
                    .header("Cache-Control", "no-cache")
                    .header("Postman-Token", "5b902520-5ebb-374d-e3e8-6430cd427742")
                    .header("Content-Length", "0")
                    .method("POST", body)
                    .build();

            // Get tne token in the response
            try (Response response = client.newCall(request).execute()) {

                JSONObject json = new JSONObject(response.body().string());
                token = json.get("access_token").toString();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServiceRTS.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Assign Token
        accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Get The TV program for RTS1 and RTS2
     *
     * @return the json program in a string
     */
    public ArrayList<String> getProgram(String channel) {

        ArrayList<String> program = new ArrayList<>();

        try {
            URL url = new URL("https://api.srgssr.ch/epg/v1/api/schedules/day?channel=" + channel);

            // Get API request
            String tokenHeader = "Bearer " + accessToken;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", tokenHeader)
                    .header("content-type", "application/json")
                    .get()
                    .build();

            // Read the response
            try (Response response = client.newCall(request).execute()) {
                JSONObject json = new JSONObject(response.body().string());
                JSONArray schedules = json.getJSONArray("schedules");
                String minutes;

                Iterator<Object> schedulesIt = schedules.iterator();
                while (schedulesIt.hasNext()) {

                    JSONObject nthSchedule = (JSONObject) schedulesIt.next();

                    JSONArray broadcasts = ((JSONArray) nthSchedule.get("broadcasts"));
                    Iterator<Object> broadcastIt = broadcasts.iterator();
                    while (broadcastIt.hasNext()) {

                        // Parsing the JSON
                        JSONObject broadcast = (JSONObject) broadcastIt.next();
                        JSONObject vps = (JSONObject) broadcast.get("vps");
                        if (vps.get("minute").toString().length() == 1) {
                            minutes = "0" + vps.get("minute");
                        } else
                            minutes = vps.get("minute").toString();
                        program.add(" : " + vps.get("hour") + ":" + minutes + " : " + broadcast.get("titles").toString());
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServiceRTS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return program;
    }

    public String getDate(String channel) {
        StringBuilder dateModifier = new StringBuilder();
        try {
            URL url = new URL("https://api.srgssr.ch/epg/v1/api/schedules/day?channel=" + channel);

            // Get API request
            String tokenHeader = "Bearer " + accessToken;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", tokenHeader)
                    .header("content-type", "application/json")
                    .get()
                    .build();

            // Read the response
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            JSONArray schedules = json.getJSONArray("schedules");
            String date = schedules.getJSONObject(0).get("date").toString();
            dateModifier= new StringBuilder(date);
            // LA date est au format yyyymmdd donc on veut inserer des separateurs aux positions 6 et 4
            dateModifier.insert(6, '/');
            dateModifier.insert(4, '/');

        } catch (IOException ex) {
            Logger.getLogger(ServiceRTS.class.getName()).log(Level.SEVERE, null, ex);

        }
        return dateModifier.toString();
    }


    @Override
    public void connect() {
        setToken();
    }

    @Override
    public void disconnect() {
    }
}

