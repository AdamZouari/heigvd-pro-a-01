import database.DatabaseController;
import database.Entities.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Home extends Application {

   public static void main(String[] args) throws SQLException {

      DatabaseController db = new  DatabaseController();
      db.connexion();
      DatabaseController.printResult(db.search());
      DatabaseController.printResult(db.getUserById(1));
      db.addUser(2,"Adam","leDZ",null, User.LANGUE.FR);
      DatabaseController.printResult(db.getUserById(2));

   }

   @Override
   public void start(Stage primaryStage) {

   }
}
