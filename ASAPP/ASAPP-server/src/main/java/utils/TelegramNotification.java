package utils;

// https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.ServiceCFF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Super site expliquant comment envoyer des notifications telegram
 * https://www.shellhacks.com/telegram-api-send-message-personal-notification-bot/ **/
public class TelegramNotification extends TelegramLongPollingBot {

    //Used for service CFF notification via telegram for ou pro_cff_bot
    private static String API_TOKEN = "807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok";


    private static String URL = "https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok/getUpdates";

    private static long PRO_CHAT_ID = (long)-397491039;

    HttpURLConnection connection;

    public String sendRuleResult(String chatID,String result){

        String rqst = "https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok/sendMessage?chat_id=" + chatID + "&text=" + result;
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
                    return "ASAPPServer Error";
            }
        } catch (
                IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static void main(String args[]){

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramNotification());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        String[] command = update.getMessage().getText().split(" ");
        SendMessage sendMessage = new SendMessage();

        if(command[0].equals("/connections")){
            // departure city
            String arg1 = command[1];
            // arrival city
            String arg2 = command[2];

            sendMessage.setText(getCffConnectionsDelays(arg1,arg2));
        }
        else {
            sendMessage.setText("Wrong");
        }
        // specify the chat ID where to send back the message
        sendMessage.setChatId(update.getMessage().getChatId());

        // send the message back to the user calling for the ASAPP BOT
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Encode message to send via telegram so that it is accepted as an URL safe message
     * **/

    public String encodeMessageForURL(String message)  {
        String encodedMessage = null;
        try {
            encodedMessage = URLEncoder.encode(message,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return API_TOKEN;
    }

    public String getCffConnections(String arg1,String arg2){

        ServiceCFF cff = new ServiceCFF();
        cff.connect();
        String trains = cff.getTrainsForPath(arg1,arg2,"2019-05-12","17:30");
        String result = "";

        result = JsonParserCFF.parseCFF(trains,arg1,arg2);


        return result;
    }

    public String getCffConnectionsDelays(String arg1,String arg2){

        ServiceCFF cff = new ServiceCFF();
        cff.connect();
        String trains = cff.getTrainsForPath(arg1,arg2,"2019-05-11","19:37");
        String result = "";

        result = JsonParserCFF.parseCFForDelay(trains,arg1,arg2);

        return result;
    }

}