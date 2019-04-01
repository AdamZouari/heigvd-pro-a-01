import database.DatabaseController;
import database.Entities.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Home extends Application {

   public static void main(String[] args) throws SQLException {

      DatabaseController db = new  DatabaseController();
      db.connexion();

      System.out.println("------ Test getUserById() ------");
      db.getUserById(0);

      System.out.println(" ------ Test addUser() ----- ");
      db.addUser(1,"Guillaume","boloss",null, User.LANGUE.FR);



      System.out.println("------ Test updateUser() ------ ");
      db.updateUser(1,"Walid","123456", null , User.LANGUE.EN);


      System.out.println("------ Test updatePassword() ------");
      db.updatePassword(1,"qwertz");

   }

   @Override
   public void start(Stage primaryStage) {

   }
}
