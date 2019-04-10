package Utils;

// https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Super site expliquant comment envoyer des notifications telegram
 * https://www.shellhacks.com/telegram-api-send-message-personal-notification-bot/ **/
public class TelegramNotification {

    //Used for service CFF notification via telegram for ou pro_cff_bot
    String API_TOKEN = "807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok";


    public static String URL = "https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok/getUpdates";

    public static String PRO_CHAT_ID = "-397491039";

    HttpURLConnection connection;

    public String sendHelloWorld(String chatID){

        String rqst = "https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6j" +
                "FEepAGRMITfhv2ok/sendMessage?chat_id=" + chatID + "&text=Hello%20World%20bis";
        try {

            URL url = new URL(rqst);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();

            switch (statusCode) {
                case 200:
                case 201:
                    return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                case 400:
                    return "Not Found";
                case 500:
                    return "Server Error";
            }
        } catch (
            IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

}
