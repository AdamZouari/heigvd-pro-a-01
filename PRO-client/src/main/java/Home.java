import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Home extends Application {

    public static void main(String[] args) {
        launch(Home.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/LoginView.fxml"));

        stage.setScene(new Scene(root));
        stage.setTitle("ASAPP");
        stage.show();

        stage.setOnCloseRequest(event -> Platform.exit());
    }

}
