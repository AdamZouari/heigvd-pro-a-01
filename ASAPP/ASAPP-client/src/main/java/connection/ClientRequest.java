package connection;

import exceptions.CustomException;
import exceptions.ProtocolException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import protocol.ExceptionCodes;
import protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;

import static protocol.Protocol.RESPONSE_FAILURE;

public class ClientRequest {

    private static final Logger LOG = Logger.getLogger(ClientRequest.class.getName());
    private static int BUFFER_SIZE = 1024;
    public static final String SALT = "1S3l5upERs0FyS7Iké";
    private static Socket clientSocket;
    private static BufferedReader reader = null;
    private static PrintWriter writer = null;
    private static String loggedUser;
    private static String URL_TELEGRAM = "https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok/getUpdates";


    public void connect(String server) throws CustomException {
        if (!isConnected()) {
            try {
                clientSocket = new Socket(server, Protocol.DEFAULT_PORT);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream());
            } catch (Exception c) {
                clientSocket = null;
                throw new CustomException(ExceptionCodes.SERVER_DOWN.ordinal());

            }

            LOG.info("Client connected to " + clientSocket.getInetAddress());
        } else {
            LOG.info("Client already connected to " + clientSocket.getInetAddress());
        }
    }

    public void register(String username, String hashPassword, String telegramUsername,int idTelegram) throws IOException, CustomException, ProtocolException {

        sendToServer(Protocol.CMD_REG + " " + username+":"+telegramUsername+":"+hashPassword+":"+idTelegram);
        String response = reader.readLine();
        checkIfSuccess(response);
    }

    public void login(String username, String hashPassword) throws IOException, CustomException, ProtocolException {

        sendToServer(Protocol.CMD_LOG + " " + username + ":" + hashPassword);
        String reponse = reader.readLine();
        checkIfSuccess(reponse);
        loggedUser = username;
    }

    private static void checkIfFail(String response) throws CustomException, ProtocolException {
        check(response, null, false);
    }

    private static void checkIfSuccess(String response) throws CustomException, ProtocolException {
        checkIfMatchStr(response, Protocol.RESPONSE_SUCCESS);
    }

    /**
     * Used to check if the response is corresponds to a certain pattern and throw an error if a failure was sent
     *
     * @param response the response to check
     * @param
     * @throws ProtocolException if a protocol error occured
     * @throws CustomException         if a GGStore application occured
     */
    private static void checkIfMatchStr(String response, String waitedMessage) throws ProtocolException,
            CustomException {
        check(response, waitedMessage, true);
    }

    private static void check(String response, String waitedMessage, boolean throwExceptionIfNotMatching) throws ProtocolException, CustomException {
        if (null == response)
            throw new NullPointerException("Response must be not null.");

        String[] items = response.split(" ");

        if (0 == items.length)
            throw new ProtocolException("Error while checking response");

        if (items[0].equals(RESPONSE_FAILURE)) {
            if (1 == items.length)
                throw new ProtocolException("Fail must provide error code");
            int errorCode = Integer.parseInt(items[1]);
            throw new CustomException(ExceptionCodes.values()[errorCode].getMessage());
        } else if (throwExceptionIfNotMatching && !items[0].equals(waitedMessage)) {
            throw new ProtocolException("Error  checking response");
        }
    }


    public void getCFF(String fromCity, String toCity, String date, String time) throws IOException, CustomException, ProtocolException {
        sendToServer(Protocol.CMD_GET_CFF + " " + fromCity+":"+toCity+":"+date+":"+time);
        String response = reader.readLine();
        checkIfSuccess(response);
    }

    // the rules result for
    public String getRulesResult() throws IOException, CustomException, ProtocolException {
        //
        sendToServer(Protocol.CMD_GET_RES_RULES + " " + loggedUser);
        String response = reader.readLine();
        checkIfSuccess(response);
        Base64.Decoder decoder = Base64.getDecoder();
        String[] s = response.split(" ");

        if(s.length == 1) {
            return "";
        }
        return new String(decoder.decode((s[1]).getBytes("utf-8")));
    }

    // the rules content
    public String getRulesContent() throws IOException, CustomException, ProtocolException {
        sendToServer(Protocol.CMD_GET_RULES + " " + loggedUser);
        String response = reader.readLine();
        checkIfSuccess(response);
        return response.substring(response.indexOf(" ") + 1);
    }



    public void addRule(String ruleToSend) throws IOException, CustomException, ProtocolException {
        sendToServer(Protocol.CMD_ADD_RULE + " " + loggedUser +" " + ruleToSend);
        String response = reader.readLine();
        checkIfSuccess(response);
    }

    public void sendToServer(String toSend) throws IOException {
        if (null == clientSocket) {
            throw new IOException("The socket is closed.");
        }
        writer.write(toSend + '\n');
        writer.flush();
        LOG.info("Sent : " + toSend);
    }

    public boolean isConnected() {
        return clientSocket != null;
    }

    public String getJsonTelegram(){
        String reponse = new String();

        try {
            URL url = new URL("https://api.telegram.org/bot807304812:AAGs_yyYLQ1f1l0rk6jFEepAGRMITfhv2ok/getUpdates");

            // Get API request
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .header("content-type", "application/json")
                    .get()
                    .build();

            // Read the response
            try (Response response = client.newCall(request).execute()) {
                JSONObject json = new JSONObject(response.body().string());
                reponse = json.toString();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        LOG.info("json : "+ reponse);
        return reponse;
    }


    public void addBot() {


        String url = "https://telegram.me/pro_cff_bot";
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();


        try{

            if (os.contains("win")) {

                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

            } else if (os.contains("mac")) {

                rt.exec( "open " + url);

            } else if (os.contains("nix") || os.contains("nux")) {

                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"epiphany","chromium" ,"firefox", "mozilla", "konqueror",
                        "netscape","opera","links","lynx"};

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i=0; i<browsers.length; i++)
                    cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");

                rt.exec(new String[] { "sh", "-c", cmd.toString() });

            } else {
                return;
            }
        }catch (Exception e){
            return;
        }
    }

    // get id for the last user that added
    public int getIdFromTelegramPseudo(String json) throws ProtocolException {

        String[] from = json.split("from");

        int id = Integer.parseInt(from[from.length - 1].split("\"id\":")[1].split(",")[0]);
        String user = from[from.length - 1].split("\"username\":\"")[1].split("\"")[0];

        if(!user.equals(loggedUser))
            throw new ProtocolException(ExceptionCodes.USER_DIDNT_ADD_TELEGRAM_BOT.getMessage());

        return id;
    }

    public void deleteUserRuleById(int ruleId) throws IOException, CustomException, ProtocolException {
        sendToServer(Protocol.CMD_DELETE_RULE + " " + loggedUser +":" + ruleId);
        String response = reader.readLine();
        checkIfSuccess(response);

    }

    public void deleteUserRules() throws IOException, CustomException, ProtocolException {
        sendToServer(Protocol.CMD_DELETE_USER_RULES + " " + loggedUser);
        String response = reader.readLine();
        checkIfSuccess(response);
    }

    public void updateLanguage(String language) throws IOException,CustomException, ProtocolException  {
        sendToServer(Protocol.CMD_SET_LANGUAGE + " " + loggedUser +":" + language);
        String response = reader.readLine();
        checkIfSuccess(response);
    }

    public String getLanguage() throws IOException, CustomException, ProtocolException {

        sendToServer(Protocol.CMD_GET_LANGUAGE + " " + loggedUser);
        String response = reader.readLine();
        checkIfSuccess(response);
        return response.split(" ")[1];
    }

    public void updatePassword(String confirmedPass) throws IOException, CustomException, ProtocolException {

        sendToServer(Protocol.CMD_SET_PASSWORD + " " + loggedUser + ":" + confirmedPass);
        String response = reader.readLine();
        checkIfSuccess(response);
    }
}
