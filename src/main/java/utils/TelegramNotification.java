package utils;

// https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started


import org.json.simple.parser.ParseException;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Super site expliquant comment envoyer des notifications telegram
 * https://www.shellhacks.com/telegram-api-send-message-personal-notification-bot/ **/
public class TelegramNotification extends TelegramLongPollingBot {

    //Used for service CFF notification via telegram for ou pro_cff_bot
    private static String API_TOKEN = "807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok";


    private static String URL = "https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok/getUpdates";

    private static String PRO_CHAT_ID = "-397491039";

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

        String command = update.getMessage().getText();
        //System.out.println(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();

        if(command.equals("connections")){
            System.out.println("arg 1" + update.getMessage().getText());
            System.out.println("arg 2" + update.getMessage().getText());
            String cff = new ServiceCFF().getTrainsForPath("Lausanne","Geneve","2019-05-09","17:30");
            try {
                JsonParserCFF.parseCFF(cff);
                sendMessage.setText(update.getMessage().getFrom().getFirstName());
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return API_TOKEN;
    }
}
