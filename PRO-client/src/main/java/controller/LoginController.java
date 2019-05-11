package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
   private ResourceBundle bundle;

   @FXML
   private Label usernameLabel;

   @FXML
   private ChoiceBox languageChoice;

   @FXML
   private void onSignUpClick() {
      try {
         Parent root = FXMLLoader.load(this.getClass().getResource("/RegisterView.fxml"));
         Stage stage = new Stage();

         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - Register");
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
      Locale locale;

      if (languageChoice.getSelectionModel().getSelectedItem().equals("English"))
         locale = new Locale("en", "EN");
      else
         locale = new Locale("fr", "FR");

      FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/LoginView.fxml"));
      fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", locale));

   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      languageChoice.getSelectionModel().selectFirst();
      bundle = resources;
//      usernameLabel.setText(bundle.getString("username"));
   }
}