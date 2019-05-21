package database;

import entities.User;

import protocol.ExceptionCodes;
import exceptions.*;

import java.sql.*;
import java.util.HashMap;
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

            int userId;
            String username, password, telegramUsername, rules;
            User.LANGUE langue;
            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                userId = Integer.parseInt(result.getString(1));
                username = result.getString(2);
                telegramUsername = result.getString(3);

                password = result.getString(4);
                rules = result.getString(5);
                langue = User.LANGUE.valueOf(result.getString(6));
                return new User(id, username, telegramUsername, password, rules, langue);

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

            int id;
            String usernameId, telegramUsername, password, rules;
            User.LANGUE langue;
            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                id = Integer.parseInt(result.getString(1));
                usernameId = result.getString(2);
                telegramUsername = result.getString(3);

                password = result.getString(4);
                rules = result.getString(5);
                langue = User.LANGUE.valueOf(result.getString(6));
                LOG.log(Level.SEVERE," User created : ", new User(id, usernameId, telegramUsername, password, rules, langue).toString());
                return new User(id, usernameId, telegramUsername, password, rules, langue);

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


    public void addUser(String username, String telegramUsername, String password, String rules, User.LANGUE langue) throws ProtocolException, CustomException {
        PreparedStatement preparedStatement = null;
        String sql = " INSERT INTO User( username, telegramUsername,password, rules, langue) VALUES (?,?,?,?,?) ;";

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
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, String.valueOf(rules));                 // TODO think of a method to pass a JSON
            preparedStatement.setString(5, langue.name());

            preparedStatement.executeUpdate();
            System.out.println("User " + username + " added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException(ExceptionCodes.REGISTRATION_FAILED.ordinal());
        }
    }

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

            System.out.println("Password of updated !");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void specifyRules(int id, String rules) {
        //JSONWriter jsonReader = Json.createReader(...);
        //JSONWriter object = jsonReader.readObject();

        // TODO return it in JSON

    }
}