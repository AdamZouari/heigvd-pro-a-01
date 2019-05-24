import database.DatabaseController;
import entities.*;
import exceptions.CustomException;
import org.apache.commons.codec.EncoderException;
import org.json.JSONArray;
import org.json.JSONObject;
import protocol.ExceptionCodes;
import protocol.Protocol;
import scheduler.RuleTask;
import scheduler.RuleTaskManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ASAPPServer {

    final static Logger LOG = Logger.getLogger(ASAPPServer.class.getName());

    private RuleTaskManager ruleTaskManager;

    private static DatabaseController db = DatabaseController.getController();

    private ASAPPServer() {
        LOG.info("Starting the RuleTaskManager...");
        ruleTaskManager = RuleTaskManager.getInstance();
    }

    private void fetchDataBaseRules() {

        Map<String, List<Rule>> userRulesMap = new HashMap<>();

        try {
            Map<String, JSONArray> userRulesStringMap = db.getAllRules();
            List<Rule> rules;

            for (String username : userRulesStringMap.keySet()) {
                JSONArray jsa = userRulesStringMap.get(username);
                rules = new ArrayList<>();

                for (Object obj : jsa) {
                    rules.add(jsonToRuleObject(username, (JSONObject) obj));
                }

                userRulesMap.put(username, rules);
            }

            ruleTaskManager.loadRules(userRulesMap);
        } catch (CustomException ce) {
            ce.printStackTrace();
        }
    }

    private void startScheduler() {
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
                    while ((shouldRun) && (line = in.readLine()) != null) {
                        LOG.info(clientSocket.getRemoteSocketAddress().toString().substring(1) + " > " + line);
                        String[] items = line.split(" ");

                        switch (items[0].toUpperCase()) {

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
                            case Protocol.CMD_GET_RES_RULES:
                                getRulesResult(items[1]);
                                break;
                            case Protocol.CMD_DELETE_RULE:
                                deleteRule(items[1]);
                                break;
                            case Protocol.CMD_DELETE_USER_RULES:
                                deleteUserRules(items[1]);
                                break;
                            case Protocol.CMD_GET_LANGUAGE:
                                getLanguage(items[1]);
                                break;
                            case Protocol.CMD_SET_LANGUAGE:
                                updateLanguage(items[1]);
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
                    int idTelegram = Integer.parseInt(creds[3]);
                    JSONObject json = new JSONObject();
                    json.put("rules",new JSONArray());
                    db.addUser(username, telegramUsername, idTelegram ,hashPassword, json.toString(), User.LANGUE.EN);
                    sendToClient(Protocol.RESPONSE_SUCCESS);

                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }
            }

            private void login(String item) {

                String[] creds = item.split(":");
                String username =  creds[0], hashPassword =  creds[1];

                try {
                    User user = DatabaseController.getController().getUserByUsername(username);

                    if(user.getHashPassword().equals(hashPassword)) {
                        sendToClient(Protocol.RESPONSE_SUCCESS);
                    } else
                        throw new CustomException(ExceptionCodes.LOGIN_FAILED.ordinal());

                } catch(CustomException e) {
                    sendError(e.getExceptionNumber());
                }
            }

            private void getRules(String username) {

                String rules = null;
                try {
                    rules = db.getUserRulesByUsername(username);
                    sendToClient(Protocol.RESPONSE_SUCCESS + " " + rules);
                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }
            }

            // TODO CHECK IF EXCEPTION POSSIBLE
            private void getRulesResult(String username) {
                String rules = ruleTaskManager.getUserTasksResults(username);
                Base64.Encoder encoder = Base64.getEncoder();
                try {
                    sendToClient(Protocol.RESPONSE_SUCCESS + " " + encoder.encodeToString(rules.getBytes("utf-8")));
                } catch (UnsupportedEncodingException uee) {
                    sendError(new CustomException(ExceptionCodes.FAIL_ENCODING.ordinal()).getExceptionNumber());
                }
            }

            private void deleteRule(String items){

                String username = items.split(":")[0];
                int ruleToDeleteId = Integer.parseInt(items.split(":")[1]);

                try {
                    db.deleteRuleById(username,ruleToDeleteId);
                    ruleTaskManager.deleteRule(username,ruleToDeleteId);
                    sendToClient(Protocol.RESPONSE_SUCCESS);
                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }

            }
            private void addRule(String username,String rules) {


                // we extracted the rules to add to the database
                JSONObject json = new JSONObject(rules);
                System.out.println(json);

                String userRulesString = null;
                try {
                    userRulesString = db.getUserRulesByUsername(username);
                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }

                // get the previous rules of a user
                JSONObject userRulesToJson = new JSONObject (userRulesString);
                JSONArray userRules = (JSONArray) userRulesToJson.get("rules");

                // Here we create the rule for the rule task manager
                ruleTaskManager.addRule(username, new RuleTask(jsonToRuleObject(username, json)));

                // Here we add the rule for the database
                userRules.put(json);

                // update old rules with new ones
                JSONObject fin = new JSONObject();
                fin.put("rules", userRules);

                // update or store new rules

                try {
                    db.updateRule(username, fin.toString());
                    sendToClient(Protocol.RESPONSE_SUCCESS);

                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }
            }

            private void deleteUserRules(String username) {
                try {
                    // delete from Database
                    db.deleteAllRuleByUsername(username);

                    //delete from taskmanager
                    ruleTaskManager.deleteAllRule(username);
                    sendToClient(Protocol.RESPONSE_SUCCESS);

                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }
            }

            private void getLanguage(String username){
                try {
                    db.getLangue(username);
                    sendToClient(Protocol.RESPONSE_SUCCESS);
                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
                }
            }

            private void updateLanguage(String items){
                try {
                    String username = items.split(":")[0];
                    String language = items.split(":")[1];

                    db.updateLanguage(username,language);
                    sendToClient(Protocol.RESPONSE_SUCCESS);
                } catch (CustomException e) {
                    sendError(e.getExceptionNumber());
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

    private Rule jsonToRuleObject(String username, JSONObject json) {
        Rule rule = null;

        int id = (int) json.get("id");
        String starting_date  = "" + (json.get("date_debut"));

        switch((String)json.get("tag")){
            case "METEO":
                rule = new MeteoRule(id,username,starting_date,(boolean)json.get("telegramNotif"),(boolean)json.get("menuNotif"),
                        (String)json.get("location"),(String)json.get("weatherType"),
                        (String)json.get("temperature"),
                        (String)json.get("temperatureSelection"));

                break;

            case "CFF":
                rule = new CffRule(id,username,starting_date,(String)json.get("from"),(String)json.get("to"),
                        (String)json.get("departureTime"),(String)json.get("arrivalTime"),(boolean)json.get("telegramNotif"),
                        (boolean)json.get("menuNotif"),(boolean)json.get("disruptionNotif"));
                break;

            case "RTS":
                rule = new RtsRule(id,username,starting_date,(String)json.get("channel"),(String)json.get("requestTime"),
                        (boolean)json.get("menuNotif"),(boolean)json.get("telegramNotif"));
                break;

            case "TWITTER":
                rule = new TwitterRule(id,username,starting_date,(String)json.get("twitterId"),
                        (boolean)json.get("menuNotif"),(boolean)json.get("telegramNotif"));
                break;

            default:
                break;
        }

        return rule;
    }


    public static void main(String[] args) {
        ASAPPServer server = new ASAPPServer();
        server.fetchDataBaseRules();
        server.startScheduler();
        server.serveClients();
    }
}