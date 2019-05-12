package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import locale.I18N;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

   @FXML
   private Label usernameLabel;

   @FXML
   private ChoiceBox languageChoice;

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