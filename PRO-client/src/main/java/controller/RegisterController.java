package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import connection.ClientRequest;
import exceptions.*;
import javafx.stage.Stage;
import utils.Crypto;

import java.io.IOException;

import static connection.ClientRequest.SALT;


public class RegisterController {
   @FXML
   private TextField username;

   @FXML
   private PasswordField password;

   @FXML
   private PasswordField passwordConfirmed;

   @FXML
   private TextField telegramUsername;

   @FXML
   public void onRegisterClick() throws CustomException, IOException, ProtocolException {

      ClientRequest cr = new ClientRequest();

      // TODO Check the length of the password ? Min 8
      // TODO check if not empty

      String username = this.username.getText();
      String password = this.password.getText();
      String passwordConfirmed = this.passwordConfirmed.getText();
      String telegramUsername = this.telegramUsername.getText();

      // TODO change exception by message in interface (label?)
      if(!password.equals(passwordConfirmed))
         throw new CustomException("Password didn't match");
      else{
         cr.register(username, Crypto.sha512(password,SALT),telegramUsername);

         // TODO refermer la fenetre de register et ouvrir celle de login
         /*try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/LoginView.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("ASAPP - Login");
            stage.show();
         } catch (IOException e) {
            System.out.println("Failed to create new Window : " + e.getMessage());
         }*/
      }

   }
}
