import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Home extends Application {

    public static void main(String[] args) {
        launch(Home.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/LoginView.fxml"));
//        Parent root = FXMLLoader.load(this.getClass().getResource("/HomeView.fxml"));

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("ASAPP");
        stage.show();

        stage.setOnCloseRequest(event -> Platform.exit());
    }
}
