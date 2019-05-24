import connection.ClientRequest;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import locale.I18N;
import protocol.Protocol;

import java.io.IOException;
import java.util.ResourceBundle;


public class ASAPPClient extends Application {
    public static void main(String[] args){

        launch(ASAPPClient.class);
    }

    @Override
    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/LoginView.fxml"));

            ClientRequest cr = new ClientRequest();

            fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));

            Parent root = fxmlLoader.load();

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("ASAPP");
            stage.show();

            stage.setOnCloseRequest(event -> Platform.exit());

            try{
                cr.connect(Protocol.HOST);

            }catch (Exception e){
                System.out.println("Server down");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
