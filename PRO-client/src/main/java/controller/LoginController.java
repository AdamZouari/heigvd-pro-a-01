package controller;

import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import protocol.ExceptionCodes;
import utils.FormUtils;

import locale.I18N;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import connection.ClientRequest;
import utils.Crypto;

import static connection.ClientRequest.SALT;

public class LoginController implements Initializable {

   @FXML
   private FlowPane pane;

   @FXML
   private Label usernameLabel;

   @FXML
   private TextField username;

   @FXML
   private Label passwordLabel;

   @FXML
   private PasswordField password;

   @FXML
   private Button login;

   @FXML
   private Label needAccount;

   @FXML
   private Hyperlink signUp;

   @FXML
   private Label language;

   @FXML
   private ChoiceBox languageChoice;

   @FXML
   private Label error;

   @FXML
   private void onSignUpClick() {
      try {
         FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/RegisterView.fxml"));
         fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));

         Parent root = fxmlLoader.load();
         Stage register = new Stage();

         register.setScene(new Scene(root));
         register.setTitle("ASAPP - " + ResourceBundle.getBundle("Internationalization", I18N.getLocale()).getString("registration"));
         register.setOnCloseRequest(event -> pane.setDisable(false));
         register.setOnHidden(event -> pane.setDisable(false));

         pane.setDisable(true);
         register.show();

      } catch (IOException e) {
         FormUtils.displayErrorMessage(error, e.getMessage());
      }
   }

   @FXML
   private void onLoginButtonClick() {
      String user = username.getText();
      String pass = password.getText();

      if(!FormUtils.isAllFilled(user, pass)){
         FormUtils.displayErrorMessage(error, ExceptionCodes.ALL_FIELDS_ARE_NOT_FILLED.getMessage());
         return;
      }

      FormUtils.hideErrorMessage(error);
      ClientRequest cr = new ClientRequest();

      try {
         cr.login(user, Crypto.sha512(pass,SALT));

         FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/HomeView.fxml"));
         fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
         Parent root = fxmlLoader.load();
         Stage stage = new Stage();

         // TODO : get Language from DB
         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - " + ResourceBundle.getBundle("Internationalization", I18N.getLocale()).getString("home"));
         stage.show();

         // Close current window
         ((Stage) this.username.getScene().getWindow()).close();

      } catch (IOException | ProtocolException | CustomException e) {
         FormUtils.displayErrorMessage(error, e.getMessage());
      }
   }

   @FXML
   private void changeLanguage() {
      if (languageChoice.getSelectionModel().getSelectedItem().equals("English"))
         I18N.setLocale(I18N.EN);
      else
         I18N.setLocale(I18N.FR);

      changeDisplayedLanguage();
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      languageChoice.getSelectionModel().selectFirst();
   }
  
   private void enableWindow() {
      pane.setDisable(false);
   }

   private void changeDisplayedLanguage() {
      ResourceBundle resource = ResourceBundle.getBundle("Internationalization", I18N.getLocale());

      usernameLabel.setText(resource.getString("username"));
      passwordLabel.setText(resource.getString("password"));
      login.setText(resource.getString("connexion"));
      needAccount.setText(resource.getString("needAccount"));
      signUp.setText(resource.getString("createAccount"));
      language.setText(resource.getString("language"));
   }
}