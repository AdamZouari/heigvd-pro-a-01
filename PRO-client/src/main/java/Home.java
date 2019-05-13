import connection.ClientRequest;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import locale.I18N;

import java.io.IOException;
import java.util.ResourceBundle;


public class Home extends Application {
    public static void main(String[] args) throws IOException {
        ClientRequest cr = new ClientRequest();
        cr.connect("127.0.0.1");

        cr.welcome();

        launch(Home.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/HomeView.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/LoginView.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));

            Parent root = fxmlLoader.load();

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("ASAPP");
            stage.show();

            stage.setOnCloseRequest(event -> Platform.exit());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
