import database.DatabaseController;
import entities.*;
import exceptions.CustomException;
import exceptions.ProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import protocol.ExceptionCodes;
import protocol.Protocol;
import scheduler.RuleTask;
import scheduler.RuleTaskManager;
import service.ServiceCFF;
import utils.JsonParserCFF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    final static Logger LOG = Logger.getLogger(Server.class.getName());

    private RuleTaskManager ruleTaskManager;

    private static DatabaseController db = DatabaseController.getController();

    private Server() {
        LOG.info("Starting the RuleTaskManager...");
        ruleTaskManager = RuleTaskManager.getInstance();
    }

    private void fetchDataBaseRules() {
        LOG.info("Fetching rules from database...");
        // ruleTaskManager.loadRules(???);
    }

    private void startScheduler() {
        LOG.info("Starting Scheduler from task manager...");
        ruleTaskManager.startScheduling();
    }

    private void serveClients() {
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

                            case Protocol.CMD_ADD_RULE:
                                addRule(items[1], items[2]);
                                break;

                            case Protocol.CMD_GET_RULES:
                                getRules(items[1]); //username
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
                    JSONObject json = new JSONObject();
                    json.put("rules",new JSONArray());
                    db.addUser(username, telegramUsername, hashPassword, json.toString(), User.LANGUE.EN);
                    sendToClient(Protocol.RESPONSE_SUCCESS);

                } catch (ProtocolException e) {
                    System.out.println(e.getMessage());

                    if(e.getMessage().equals(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO.getMessage()))
                        sendError(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO.ordinal());
                    else
                        sendError(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_TELEGRAM.ordinal());
                // TODO : Check CustomException ? Send to client ?
                } catch (CustomException e) {
                    System.out.println(e.getMessage());

                }
            }

            private void login(String item) throws SQLException {

                String[] creds = item.split(":");
                String username =  creds[0], hashPassword =  creds[1];

                try {
                    User user = DatabaseController.getController().getUserByUsername(username);

                    if(user.getHashPassword().equals(hashPassword)) {
                        System.out.println("User " + username + " logged");
                        sendToClient(Protocol.RESPONSE_SUCCESS);
                    } else
                        sendError(ExceptionCodes.LOGIN_FAILED.ordinal());

                } catch(Exception e) {
                    sendError(ExceptionCodes.LOGIN_FAILED.ordinal());
                }
            }

            private void cff(String item) {

                ServiceCFF cff = new ServiceCFF();
                cff.connect();
                String[] params = item.split(":");

                String connectionsFound = cff.getTrainsForPath(params[0],params[1],params[2],params[3]);

                if(connectionsFound != null){

                    String parsedMsg = JsonParserCFF.parseCFF(connectionsFound,params[0],params[1]);

                    sendToClient(Protocol.RESPONSE_SUCCESS);
                }else {
                    sendToClient(Protocol.RESPONSE_FAILURE);
                }

                cff.disconnect();

            }



            private void getRules(String username) throws SQLException {

                String rules = db.getUserRulesByUsername(username);
                sendToClient(Protocol.RESPONSE_SUCCESS + " " + rules);

            }


            private void addRule(String username,String rules) throws SQLException {


                LOG.info("rules " + rules);
                // we extracted the rules to add to the database
                JSONObject json = new JSONObject(rules);
                System.out.println(json);

                // TODO create new Entities.Rule object to add to list of all rules
                // iterate to switch whether it is a cff,rts,... rule


                String userRulesString = db.getUserRulesByUsername(username);

                // get the previous rules of a user
                JSONObject userRulesToJson = new JSONObject (userRulesString);
                JSONArray userRules = (JSONArray) userRulesToJson.get("rules");

                // Here we create the rule for the rule task manager

                Rule rule = null;
                int id = (int) json.get("id");
                String starting_date  = "" + (json.get("date_debut"));
                // TODO parse in function of the tag (if tag == meteo then)
                switch((String)json.get("tag")){
                    case "METEO":
                        //  parse all infos for meteo
                        rule = new MeteoRule(id,starting_date,(boolean)json.get("telegramNotif"),(boolean)json.get("menuNotif"),
                                (String)json.get("time"), (String)json.get("location"),(String)json.get("weatherType"),
                                (String)json.get("temperature"),
                                (String)json.get("temperatureSelection"),(String)json.get("noteText"));
                        break;
                    case "CFF":
                        //  parse all infos for meteo
                        rule = new CffRule(id,starting_date,(String)json.get("from"),(String)json.get("to"),
                                (String)json.get("departureTime"),(String)json.get("arrivalTime"),(boolean)json.get("telegramNotif"),
                                (boolean)json.get("menuNotif"),(boolean)json.get("disruptionNotif"));
                        break;
                    case "RTS":
                        //  parse all infos for meteo
                        rule = new RtsRule(id,starting_date,(String)json.get("channel"),(String)json.get("requestTime"),
                                (boolean)json.get("menuNotif"),(boolean)json.get("telegramNotif"));
                        break;
                    case "TWITTER":
                        //  parse all infos for meteo
                        rule = new TwitterRule(id,starting_date,(String)json.get("twitterId"),(String)json.get("pin"),
                                (boolean)json.get("menuNotif"),(boolean)json.get("telegramNotif"));
                        break;
                    default:
                        break;
                }

                ruleTaskManager.addRule(username, new RuleTask(rule));

                // Here we add the rule for the database
                userRules.put(json);

                // update old rules with new ones
                JSONObject fin = new JSONObject();
                fin.put("rules", userRules);

                // update or store new rules
                db.updateRule(username, fin.toString());
                //TODO christoph ? need of a rule
                //Rule ruleToAdd = new CffRule();
                //allRUles.add(ruleToAdd);

                sendToClient(Protocol.RESPONSE_SUCCESS);

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
        server.fetchDataBaseRules(); // does nothing right now
        server.startScheduler();
        server.serveClients();
    }
}