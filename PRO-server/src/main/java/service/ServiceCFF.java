package service;

import utils.JsonParserCFF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;


// Here we do not need an API key nor a OAuth token, the CFF API is public
// the only limit he have is that the number of requests per day and per client (ip adress) is limited to 1000 requests
public class ServiceCFF extends Service {


    /** Nous voulons pouvoir récupérer les horaires pour un itinéraire donné.
     * Nous voulons également envoyer une notification sur Telegram en cas de perturbation sur le trajet.
     *
     **/

    private final String urlService = "http://transport.opendata.ch/v1/connections";
    HttpURLConnection connection;

    public ServiceCFF() {

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

    //Nous voulons pouvoir récupérer les horaires pour un itinéraire donné.

    // cf: https://transport.opendata.ch/docs.html#connections

    /**
     * Function that allows to obtain connections between to cities
     * It returns a number of connection and
     * We get the time of departure and the "quai", and the time of arrival and the "quai"
     * By default we chose limit those connections to 2
     * ex: if someone wants to go from Lausanne to Geneva, the two first connections will be presented to him
     * **/
    //
    public String getTrainsForPath(String fromCity, String toCity, String date, String time){
        // here we limit the accepted city values to city names otherwise asks to "saisir" again the city names
        try{
            // Create url to get the request

            /**We need the format "Lausanne" or "Geneve" for cities**/
            String urlRequest = urlService + "?from=" + fromCity + "&to=" + toCity + "&date=" + date
                                + "&time=" + time + "&limit=2" + "&direct=1&transportations[]=train";
            // to update : toCity.toLowerCase().charAt(0).toUppercase()
            URL url = new URL(urlRequest);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            fromCity = fromCity.toLowerCase();
            toCity = toCity.toLowerCase();

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

    public void NotifyToTelegram(){

    }

    @Override
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