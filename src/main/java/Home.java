import database.DatabaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Home extends Application {

   public static void main(String[] args) {


      System.out.println("COUCOU");
      DatabaseController.connexion();
      DatabaseController.search();
   }

   @Override
   public void start(Stage primaryStage) {

   }
}
