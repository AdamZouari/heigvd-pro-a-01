package controller;

import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import connection.ClientRequest;
import utils.Crypto;

import static connection.ClientRequest.SALT;


public class LoginController {

   @FXML
   private TextField username;

   @FXML
   private PasswordField password;

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

      ClientRequest cr = new ClientRequest();

      //TODO check if not empty
      try {
         cr.login(username.getText(), Crypto.sha512(password.getText(),SALT));

         Parent root = FXMLLoader.load(this.getClass().getResource("/HomeView.fxml"));
         Stage stage = new Stage();

         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - Register");
         stage.show();
      } catch (IOException e) {
         e.getMessage();
      } catch (ProtocolException e) {
         e.printStackTrace();
      } catch (CustomException e) {
         e.printStackTrace();
      }


   }
}