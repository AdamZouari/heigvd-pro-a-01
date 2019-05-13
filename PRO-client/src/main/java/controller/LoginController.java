package controller;

import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.ExceptionCodes;
import utils.CheckForm;

import javafx.scene.control.ChoiceBox;
import locale.I18N;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import connection.ClientRequest;
import utils.Crypto;

import static connection.ClientRequest.SALT;

public class LoginController implements Initializable {

   @FXML
   private ChoiceBox languageChoice;

   @FXML
   private TextField username;

   @FXML
   private PasswordField password;

   @FXML
   private Label error;

   @FXML
   private void onSignUpClick() {
      try {
         FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/RegisterView.fxml"));
         fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
         Parent root = fxmlLoader.load();
         Stage stage = new Stage();

         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - " + ResourceBundle.getBundle("Internationalization", I18N.getLocale()).getString("registration"));
         stage.show();
      } catch (IOException e) {
         System.out.println("Failed to create new Window : " + e.getMessage());
      }
   }

   @FXML
   private void onLoginButtonClick() {
      String user = username.getText();
      String pass = password.getText();

      if(!CheckForm.isAllNotEmpty(user, pass)){
         error.setText(ExceptionCodes.ALL_FIELDS_ARE_NOT_FILLED.getMessage());
         error.setVisible(true);
         return;
      }

      error.setVisible(false);
      ClientRequest cr = new ClientRequest();

      // TODO : Afficher erreur du serveur
      try {
         cr.login(user, Crypto.sha512(pass,SALT));

         FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/HomeView.fxml"));
         fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
         Parent root = fxmlLoader.load();
         Stage stage = new Stage();

         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - " + ResourceBundle.getBundle("Internationalization", I18N.getLocale()).getString("home"));
         stage.show();
      } catch (IOException e) {
         e.getMessage();
      } catch (ProtocolException e) {
         e.printStackTrace();
      } catch (CustomException e) {
         e.printStackTrace();
      }
   }

   @FXML
   private void changeLanguage() {

      if (languageChoice.getSelectionModel().getSelectedItem().equals("English")) {
         I18N.setLocale(I18N.EN);
      } else {
         I18N.setLocale(I18N.FR);
      }

      FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/LoginView.fxml"));
      fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      languageChoice.getSelectionModel().selectFirst();
//      usernameLabel.setText(bundle.getString("username"));
   }
}