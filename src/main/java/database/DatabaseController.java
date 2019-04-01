package database;

import com.oracle.javafx.jmx.json.JSONDocument;
import com.oracle.javafx.jmx.json.JSONWriter;
import com.sun.tools.javac.code.Attribute;
import database.Entities.User;

import java.sql.*;

public class DatabaseController {

    private static Connection mConnection;
    private static String url = "jdbc:mysql://35.181.66.143:3306/pro2019?user=pro2019&password=Hooch$fizz$onion$emits$3Cede$Bloat";

    //QueryRunner run = new QueryRunner(dataSource);


    public static void connexion() {
        try {
            mConnection = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet search() {
            Statement statement;
            //PreparedStatement preparedStatement = null;
            ResultSet result = null;
            try {


                statement = mConnection.createStatement();

                String sql = " SELECT *"
                        +" FROM User ;";

                result = statement.executeQuery(sql);

                //ResultSetMetaData rsmd = result.getMetaData();

                // printResult();

            } catch (Exception e) {
                System.out.println("erreur query1" + e.getMessage());
                return null;
            }
            return result;

    }


    public ResultSet getUserById(int id){

        Statement statement;
        //PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {

            statement = mConnection.createStatement();

            String sql = " SELECT * FROM User WHERE id = " + id + ";";

           result = statement.executeQuery(sql);


        } catch (Exception e) {
            System.out.println("erreur query1" + e.getMessage());
        }

        return result;
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

    public static ResultSet addUser(int id, String username, String password, JSONDocument rules, User.LANGUE langue){


        PreparedStatement statement;
        ResultSet result = null;

        try {


            String sql = " INSERT INTO User(`id`, `username`, `password`, `rules`, `langue`)" +
                    " VALUES (" +  id + "," + username + "," + password + ","
                    + rules + "," + langue.name() + ")";

            String sql2 = " INSERT INTO User(`id`, `username`, `password`, `rules`, `langue`)" +
                    " VALUES (?,?,?,?,?) ;";

            statement = mConnection.prepareStatement(sql2);
            statement.setInt(1,id); // TODO replace it by a random id
            statement.setString(2,username);
            statement.setString(3,password);
            statement.setString(4, String.valueOf(rules));                 // TODO think of a method to pass a JSON
            statement.setString(5, langue.name());

            result = statement.executeQuery(sql2);


        } catch (Exception e) {
            System.out.println("erreur query add user" + e.getMessage());
        }

        return result;

    }

    public static ResultSet updateUserById(int id,String username, String password, JSONDocument rules, User.LANGUE langue){


        Statement statement;
        ResultSet result = null;

        try {

            statement = mConnection.createStatement();

            String sql = "UPDATE `User` SET `username`=" + username + ",`password`=" + password + ",`rules`=" + rules
                    + ",`langue`=" + langue + " WHERE `id` = " + id + ";";


            result = statement.executeQuery(sql);


        } catch (Exception e) {
            System.out.println("erreur query update user" + e.getMessage());
        }

        return result;
    }

    public static ResultSet resetPassword(int id,String password){
        Statement statement;
        ResultSet result = null;

        try {

            statement = mConnection.createStatement();

            String sql = "UPDATE `User` SET `password`=" + password + " WHERE `id` = " + id + ";";


            result = statement.executeQuery(sql);


        } catch (Exception e) {
            System.out.println("erreur query reset password" + e.getMessage());
        }
        return result;
    }

    public static void specifyRules(int id,String rules){
        //JSONWriter jsonReader = Json.createReader(...);
        //JSONWriter object = jsonReader.readObject();

        // TODO return it in JSON

    }
}