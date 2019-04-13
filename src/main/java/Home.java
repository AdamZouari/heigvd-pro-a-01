package main.java;

import main.java.database.DatabaseController;
import main.java.database.Entities.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Home extends Application {

   public static void main(String[] args) throws SQLException {

      DatabaseController db = DatabaseController.getController();

      System.out.println(" ------ Test addUser() ----- ");
      db.addUser("Guillaume","guitch",null, User.LANGUE.FR);
      db.addUser("Walid","zedsdead",null, User.LANGUE.EN);

      System.out.println("------ Test getUserById() ------");
      db.getUserById(0);

      System.out.println("------ Test updateUser() ------ ");
      db.updateUser(1,"Valid","zedsdead", null , User.LANGUE.EN);


      System.out.println("------ Test updatePassword() ------");
      db.updatePassword(0,"guitch94");

   }

   @Override
   public void start(Stage primaryStage) {

   }
}
