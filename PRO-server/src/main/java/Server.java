import database.DatabaseController;
import database.Entities.User;
import protocol.ExceptionCodes;
import protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    final static Logger LOG = Logger.getLogger(Server.class.getName());

    public void serveClients() {
        new Thread(new ReceptionistWorker()).start();
    }

    private class ReceptionistWorker implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket;

            try {
                serverSocket = new ServerSocket(Protocol.DEFAULT_PORT);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            while (true) {
                LOG.info("Waiting (blocking) for a new client...");
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ServantWorker(clientSocket)).start();
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }

        }

        private class ServantWorker implements Runnable {

            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;

            public ServantWorker(Socket clientSocket) {
                try {
                    this.clientSocket = clientSocket;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }

            @Override
            public void run() {
                String line;
                boolean shouldRun = true;

                try {
                    LOG.info("Reading until client sends BYE or closes the connection...");
                    //TODO CHANGER COMPORTEMENT, RENVOIE SEULEMENT CE QUE LE CLIENT LUI ENVOI
                    while ((shouldRun) && (line = in.readLine()) != null) {
                        LOG.info(clientSocket.getRemoteSocketAddress().toString().substring(1) + " > " + line);
                        String[] items = line.split(" ");

                        switch (items[0].toUpperCase()) {

                            case Protocol.CMD_WELCOME:
                                sendToClient("WEEEEEELCOOOME");
                                break;

                            case Protocol.CMD_REG:
                                register(items[1]);
                                break;

                            case Protocol.CMD_LOG:
                                login(items[1]);
                                break;

                        }

                    }

                    LOG.info("Cleaning up resources...");
                    in.close();
                    out.close();
                    clientSocket.close();

                } catch (IOException ex) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex.getMessage(), ex1);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            private void sendToClient(String toSend) {
                out.println(toSend);
                out.flush();
            }

            private void register(String item) {

                try {
                    String[] creds = item.split(":");
                    String username =  creds[0], telegramUsername =  creds[1], hashPassword = creds[2];

                    DatabaseController db = DatabaseController.getController();
                    db.addUser(username, telegramUsername, hashPassword, null, User.LANGUE.EN);
                    sendToClient(Protocol.RESPONSE_SUCCESS);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    sendError(ExceptionCodes.REGISTRATION_FAILED.ordinal());
                }
            }
            private void login(String item) throws SQLException {

                String[] creds = item.split(":");
                String username =  creds[0], hashPassword =  creds[1];

                User user = DatabaseController.getController().getUserByUsername(username);
                if(user.getHashPassword().equals(hashPassword)){
                    System.out.println("User " +username+ " logged");
                    sendToClient(Protocol.RESPONSE_SUCCESS);
                }

            }

            /**
             * Send an error to clients
             *
             * @param i the error code number
             */
            private void sendError(int i) {
                sendToClient(Protocol.RESPONSE_FAILURE + " " + i);
            }
        }
    }



    public static void main(String[] args) {
        System.out.println("This is the server");
        Server server = new Server();
        server.serveClients();
    }
}