package service;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceRTS extends Service {

    public ServiceRTS() { }

    private String accessToken;
    final private String urlToken = "https://api.srgssr.ch/oauth/v1/accesstoken?grant_type=client_credentials";

    /**
     * Set accessToken to access to the RTS API content
     */
    public void setToken() {

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String token   = new String();
        String answer  = new String();

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
     * Get The TV program
     * @return the json program in a string
     */
    public String getProgram() {

        String program = new String();

        try {
            URL url = new URL("https://api.srgssr.ch/epg/v1/api/schedules/day");

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
                program = json.toString();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServiceRTS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return program;
    }


    @Override
    public void connect() {

    }



    @Override
    public void disconnect() {

    }



}

