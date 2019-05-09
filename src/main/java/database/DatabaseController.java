package database;

import database.Entities.User;

import java.sql.*;

// TODO Return objects
public class DatabaseController {

    private static DatabaseController controller;
    private static Connection mConnection;
    private static String url;
    private static int id = 0;
    //QueryRunner run = new QueryRunner(dataSource);


    private DatabaseController(){

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

    public static DatabaseController getController(){

        if(controller == null)
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


    public ResultSet getUserById(int id) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String sql = " SELECT * FROM User WHERE id = ?";

        try {

            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("user got ");

        return result;
    }


    // TODO : Check if exist
    public void addUser(String username, String password, String rules, User.LANGUE langue) {


        PreparedStatement preparedStatement = null;
        String sql = " INSERT INTO User(id, username, password, rules, langue)" +
                " VALUES (?,?,?,?,?) ;";

        try {


            preparedStatement = mConnection.prepareStatement(sql);
            preparedStatement.setInt(1, id++); // TODO replace it by a random id
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, String.valueOf(rules));                 // TODO think of a method to pass a JSON
            preparedStatement.setString(5, langue.name());

            preparedStatement.executeUpdate();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("User" + username + " added !");

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


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("User " + username +" updated !");

    }

    public void updatePassword(int id, String password) {

        PreparedStatement preparedStatement = null;
        String sql = "UPDATE User SET password = ?  WHERE id = ? ";


        try {

            preparedStatement = mConnection.prepareStatement(sql);

            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Password of updated !");

    }

    public void specifyRules(int id, String rules) {
        //JSONWriter jsonReader = Json.createReader(...);
        //JSONWriter object = jsonReader.readObject();

        // TODO return it in JSON

    }
}