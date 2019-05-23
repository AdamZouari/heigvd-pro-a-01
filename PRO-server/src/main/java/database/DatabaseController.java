package database;

import entities.User;

import protocol.ExceptionCodes;
import exceptions.*;
import sun.rmi.runtime.Log;

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

    /*public static void printResult(ResultSet result) throws SQLException {

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
    }*/

    /*public ResultSet search() {

        Statement statement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User ;";

        try {
            result = statement.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }*/


    public User getUserById(int id) throws CustomException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE id = ?";
        User user = null;

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
                user = new User(id, username, telegramUsername, idTelegram,password, rules, langue);
            }

        } catch (SQLException e) {
            throw new CustomException(ExceptionCodes.FAIL_TO_FETCH_USER_FROM_DB.ordinal());
        }

        return user;
    }

    public User getUserByUsername(String username) throws CustomException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE username = ?";
        User user = null;


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
                user = new User(id, usernameId, telegramUsername, idTelegram, password, rules, langue);
            }

        } catch (SQLException e) {
            throw new CustomException(ExceptionCodes.FAIL_TO_FETCH_USER_FROM_DB.ordinal());
        }
        return user;

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


    public void addUser(String username, String telegramUsername, int idTelegram ,String password, String rules, User.LANGUE langue) throws CustomException {
        PreparedStatement preparedStatement = null;
        String sql = " INSERT INTO User( username, telegramUsername,idTelegram,password, rules, langue) VALUES (?,?,?,?,?,?) ;";

        try {
            if (usernameExist(username)) {
                throw new CustomException(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO.ordinal());
            }

            if(usernameTelegramExist(telegramUsername)){
                throw new CustomException(ExceptionCodes.A_USER_ALREADY_EXISTS_WITH_THIS_TELEGRAM.ordinal());
            }

            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, telegramUsername);
            preparedStatement.setInt(3, idTelegram);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, String.valueOf(rules));
            preparedStatement.setString(6, langue.name());

            preparedStatement.executeUpdate();
            LOG.log(Level.INFO,"User "+username+ " added.");
        } catch (SQLException e) {
            throw new CustomException(ExceptionCodes.REGISTRATION_FAILED.ordinal());
        }
    }

    public void updateUser(int id, String username, String password, String rules, User.LANGUE langue) throws CustomException {
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
            LOG.log(Level.INFO,"User "+username+ " updated.");
        } catch (SQLException e) {
            throw new CustomException(ExceptionCodes.UPDATE_OF_USER_FAILED.ordinal());
        }
    }

    public String getUserRulesByUsername(String username) throws CustomException {
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
        }catch(SQLException e){
            throw new CustomException(ExceptionCodes.FAIL_TO_FETCH_USER_FROM_DB.ordinal());
        }
        return userRules;
    }

    public Map<String, String> getAllRules() throws CustomException {
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
        }catch(SQLException e){
            throw new CustomException(ExceptionCodes.FAIL_TO_FETCH_RULES_FROM_DB.ordinal());
        }

        return allRules;
    }


    public void updateRule(String username, String rule) throws CustomException {

        PreparedStatement preparedStatement = null;
        String sql = "UPDATE User SET rules = ?  WHERE username = ? ";

        try {

            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setString(1, rule);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
            LOG.log(Level.INFO,"Rule of  "+username+ " updated.");

        } catch (SQLException e) {
            throw new CustomException(ExceptionCodes.UPDATE_OF_RULE_FAILED.ordinal());
        }
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