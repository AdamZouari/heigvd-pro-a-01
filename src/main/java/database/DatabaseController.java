package database;

import database.Entities.User;

import java.sql.*;

public class DatabaseController {

    private static Connection mConnection;
    private static String url = "jdbc:mysql://127.0.0.1:3308/pro2019?user=user&password=password";

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

    public static void addUser(){

    }

    public static void updateUser(){

    }

    public static void setPassword(){

    }
}