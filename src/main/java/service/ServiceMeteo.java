package main.java.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jdk.nashorn.internal.parser.JSONParser;

public class ServiceMeteo extends Service {

    private final String urlService = "http://api.openweathermap.org/data/2.5/";
    private final String apiKey = "&APPID=d13a3ef0bca22b7575956470654280e4"; // Don't let it here
    HttpURLConnection connection;

    public ServiceMeteo() {

    }

    @Override
    public void connect() {

        try {
            URL url = new URL(urlService);
            connection = (HttpURLConnection) url.openConnection();
            System.out.println("Connection okay");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public String getWeather(String city) {
        try {
            // Create url to get the request
            String urlRequest = urlService + "weather?q=" + city + apiKey;
            URL url = new URL(urlRequest);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            System.out.println(statusCode);
            switch (statusCode) {
                case 200:
                case 201:
                    return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                case 400:
                    return "Not Found";
                case 500:
                    return "Server Error";
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }


    public void disconnect() {
        try {
            URL url = new URL(urlService);
            connection = (HttpURLConnection) url.openConnection();
            connection.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
