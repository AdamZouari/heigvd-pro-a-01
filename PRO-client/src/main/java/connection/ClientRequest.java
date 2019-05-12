package connection;

import exceptions.CustomException;
import exceptions.ProtocolException;
import org.apache.log4j.Logger;
import protocol.ExceptionCodes;
import protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import static protocol.Protocol.RESPONSE_FAILURE;

public class ClientRequest {

    private static final Logger LOG = Logger.getLogger(ClientRequest.class.getName());
    private static int BUFFER_SIZE = 1024;
    public static final String SALT = "1S3l5upERs0FyS7Iké";
    private static Socket clientSocket;
    private static BufferedReader reader = null;
    private static PrintWriter writer = null;

    public void connect(String server) throws IOException {
        if (!isConnected()) {
            try {
                clientSocket = new Socket(server, Protocol.DEFAULT_PORT);
            } catch (ConnectException c) {
                clientSocket = null;
                return;
            }
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());

            LOG.info("Client connected to " + clientSocket.getInetAddress());
        } else {
            LOG.info("Client already connected to " + clientSocket.getInetAddress());
        }
    }

    public void register(String username, String hashPassword, String telegramUsername) throws IOException, CustomException, ProtocolException {

        sendToServer(Protocol.CMD_REG + " " + username+":"+telegramUsername+":"+hashPassword);
        String response = reader.readLine();
        checkIfSuccess(response);

    }

    public void login(String username, String hashPassword) throws IOException, CustomException, ProtocolException {

        sendToServer(Protocol.CMD_LOG + " " + username + ":" + hashPassword);
        String reponse = reader.readLine();
        checkIfSuccess(reponse);
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



    // the rules result for
    public void getRulesResult(String username) {
        //
    }

    // the rules content
    public void getRulesContent(String username) {

    }

    public void createNewRule() {

    }

    public void sendRule() {

    }

    public void welcome() throws IOException {
        sendToServer("Welcome");
        LOG.info("Got : " + reader.readLine());

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
}
