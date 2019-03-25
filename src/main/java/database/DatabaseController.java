package database;

import java.sql.*;

public class DatabaseController {

    private static Connection mConnection;
    private static String url = "jdbc:mysql://localhost:3308/pro2019?user=user&password=password";


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
        try {


            statement = mConnection.createStatement();

            String sql = " SELECT *"
                    +" FROM user ;";

            ResultSet result = statement.executeQuery(sql);

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
            return result;

        } catch (Exception e) {
            System.out.println("erreur query1" + e.getMessage());
            return null;
        }

    }
}