package database;

import entities.User;

import org.json.JSONArray;
import org.json.JSONObject;
import protocol.ExceptionCodes;
import exceptions.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseController {

    private static DatabaseController controller;
    private static Connection mConnection;
    private static String url;

    final static Logger LOG = Logger.getLogger(DatabaseController.class.getName());


    private DatabaseController() {

        // mysql -u pro2019 -p -h pro2019.c0owyd1iitne.eu-west-3.rds.amazonaws.com to access to the db
        DatabaseController.url = "jdbc:mysql://pro2019.c0owyd1iitne.eu-west-3.rds.amazonaws.com:3306/pro2019?user=pro2019&password=Hooch$fizz$onion$emits$3Cede$Bloat";
        DatabaseController.connexion();

    }

    public static void connexion() {
        try {
            mConnection = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseController getController() {

        if (controller == null)
            controller = new DatabaseController();
        return controller;

    }

    public static void printResult(ResultSet result) throws SQLException {

        ResultSetMetaData rsmd = result.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = result.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

    public ResultSet search() {

        Statement statement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User ;";

        try {

            result = statement.executeQuery(sql);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public User getUserById(int id) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE id = ?";


        try {

            int userId,idTelegram;
            String username, password, telegramUsername, rules;
            User.LANGUE langue;
            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                userId = Integer.parseInt(result.getString(1));
                username = result.getString(2);
                telegramUsername = result.getString(3);
                idTelegram = result.getInt(4);
                password = result.getString(5);
                rules = result.getString(6);
                langue = User.LANGUE.valueOf(result.getString(7));
                return new User(id, username, telegramUsername, idTelegram,password, rules, langue);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public User getUserByUsername(String username) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE username = ?";


        try {

            int id, idTelegram;
            String usernameId, telegramUsername, password, rules;
            User.LANGUE langue;
            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                id = Integer.parseInt(result.getString(1));
                usernameId = result.getString(2);
                telegramUsername = result.getString(3);
                idTelegram = result.getInt(4);

                password = result.getString(5);
                rules = result.getString(6);
                langue = User.LANGUE.valueOf(result.getString(7));
                return new User(id, usernameId, telegramUsername, idTelegram, password, rules, langue);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public boolean usernameExist(String username) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE username = ?";

        preparedStatement = mConnection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        result = preparedStatement.executeQuery();

        return result.next();


    }

    public boolean usernameTelegramExist(String usernameTelegram) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE telegramUsername = ?";

        preparedStatement = mConnection.prepareStatement(sql);

        preparedStatement.setString(1, usernameTelegram);
        result = preparedStatement.executeQuery();

        return result.next();

    }


    public void addUser(String username, String telegramUsername, int idTelegram ,String password, String rules, User.LANGUE langue) throws ProtocolException, CustomException {
        PreparedStatement preparedStatement = null;
        String sql = " INSERT INTO User( username, telegramUsername,idTelegram,password, rules, langue) VALUES (?,?,?,?,?,?) ;";

        try {
            if (usernameExist(username)) {
                throw new ProtocolException(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO.getMessage());
            }

            if(usernameTelegramExist(telegramUsername)){
                throw new ProtocolException(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_TELEGRAM.getMessage());
            }

            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, telegramUsername);
            preparedStatement.setInt(3, idTelegram);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, String.valueOf(rules));                 // TODO think of a method to pass a JSON
            preparedStatement.setString(6, langue.name());

            preparedStatement.executeUpdate();
            System.out.println("User " + username + " added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException(ExceptionCodes.REGISTRATION_FAILED.ordinal());
        }
    }

    // TODO check idTelegram
    public void updateUser(int id, String username, String password, String rules, User.LANGUE langue) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE User SET username = ? , password = ? , rules = ? , langue = ? " +
                " WHERE id = ? ";

        try {
            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, rules);
            preparedStatement.setString(4, langue.name());
            preparedStatement.setInt(5, id);


            preparedStatement.executeUpdate();
            System.out.println("User " + username + " updated !");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public String getUserRulesByUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = "SELECT rules FROM User WHERE username=?";
        String userRules = "none";

        try {

            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                userRules = result.getString(1);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return userRules;
    }

    // return all rules for each user as a Map<Username,JSONArray
    public Map<String, String> getAllRules() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT username,rules FROM User";
        Map<String,String> allRules = null;

        try {
            allRules = new HashMap<>();
            String username;
            preparedStatement = mConnection.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                String userRules;
                username = result.getString(1);
                userRules = result.getString(2);
                allRules.put(username,userRules);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return allRules;
    }

    public void updatePassword(int id, String password) {

        PreparedStatement preparedStatement = null;
        String sql = "UPDATE User SET password = ?  WHERE id = ? ";


        try {
            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            System.out.println("Password of updated !");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void updateRule(String username, String rule) {


        PreparedStatement preparedStatement = null;
        String sql = "UPDATE User SET rules = ?  WHERE username = ? ";


        try {

            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setString(1, rule);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
            System.out.println("Rule of updated !");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRuleById(String username, int ruleToDeleteId) throws CustomException {

        PreparedStatement preparedStatement = null;
        String sql = "UPDATE rules FROM User WHERE username = ?";

        try {

            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            // Here we parse the rule to delete so we can get its id


            // Get all rules
            JSONObject json = new JSONObject(sql);
            JSONArray userRules = ((JSONArray)json.get("rules"));

            // user rule
            Iterator<Object> userRuleIt = userRules.iterator();
            int i = 0;
            while (userRuleIt.hasNext()) {

                JSONObject nthRule = (JSONObject) userRuleIt.next();
                // if rule is the rule to delete then we dont add it to the new array of rules for the user
                if(nthRule.get("id").equals(ruleToDeleteId)){
                    // remove the correspondant rule
                    userRules.remove(i);
                }
                i++;
            }

            preparedStatement.executeUpdate();
            System.out.println("Rule deleted !");

            /** replace all the old rules with the new ones minus the deleted rule
                here we have no other choice than to do that because we are dealing with JSONObjects
            **/
             JSONObject updatedRules = new JSONObject();
             updatedRules.put("rules", updatedRules);

             // update old rules with new rules
             updateRule(username, updatedRules.toString());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // throw custom exception
        }
    }

    public void deleteAllRuleByUsername(String username) {

            JSONObject emptyRules = new JSONObject();
            // create empty rules
            emptyRules.put("rules", new JSONArray());

            updateRule(username, emptyRules.toString());

    }

    public String getTelegramIdByUsername(String username) {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = "SELECT idTelegram FROM User WHERE username=?";
        int idTelegram = -1;

        try {

            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            result = preparedStatement.executeQuery();

            if(result.next())
                idTelegram = result.getInt(1);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return "" + idTelegram;

    }
}