package service;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceMeteo extends Service {

    private final String urlService = "http://api.openweathermap.org/data/2.5/";
    private final String apiKey = "&APPID=d13a3ef0bca22b7575956470654280e4"; // Don't let it here
    HttpURLConnection connection;

    public ServiceMeteo() {}

    @Override
    public void connect() { }

    @Override
    public void disconnect() {}

    private int KelvinToCelsus(int kelvin) {
        return kelvin - 273;
    }

    private String getWeather(String city) {
        try {
            // Create url to get the request
            String urlRequest = urlService + "weather?q=" + city + apiKey;
            URL url = new URL(urlRequest);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            //System.out.println(statusCode);
            switch (statusCode) {
                case 200:
                case 201:
                    return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                case 400:
                    return "Not Found";
                case 500:
                    return "ASAPPServer Error";
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public String getForecast(String city) {
        try {
            // Create url to get the request
            String urlRequest = urlService + "forecast?q=" + city + apiKey;
            URL url = new URL(urlRequest);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            //System.out.println(statusCode);
            switch (statusCode) {
                case 200:
                case 201:
                    return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                case 400:
                    return "Not Found";
                case 500:
                    return "ASAPPServer Error";
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }


    public int getTemperature(String city) {
        // Fetch Data
        JSONObject jsonData = new JSONObject(getWeather(city));

        // return information
        return KelvinToCelsus(jsonData.getJSONObject("main").getInt("temp"));
    }

    public String getMain(String city) {
        // Fetch Data from API
        JSONObject jsonData = new JSONObject(getWeather(city));

        // Return the main information :
        return jsonData.getJSONArray("weather").getJSONObject(0).getString("main");
    }
    public boolean isCloudy(String city) { return getMain(city).contains("Cloud");}
    public boolean isSunny(String city) { return getMain(city).contains("Clear");}
    public boolean isRainy(String city) { return getMain(city).contains("Rain");}
    public boolean isSnowy(String city) { return getMain(city).contains("Snow");}
}
