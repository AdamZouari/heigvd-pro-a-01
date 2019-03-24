package database;

import java.sql.*;

public class DatabaseController {

    private static Connection mConnection;
    private static String url = "jdbc:mysql://localhost:9999/pro2019?user=user&password=password";


    public static void connexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mConnection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet search() {
        Statement statement;
        //PreparedStatement preparedStatement = null;
        try {


            statement = mConnection.createStatement();

            String sql = " SELECT *"
                    +" FROM USER ;";

            ResultSet result = statement.executeQuery(sql);
            System.out.println(result.toString());
            return result;

        } catch (Exception e) {
            System.out.println("erreur query1");
            return null;
        }

    }
}