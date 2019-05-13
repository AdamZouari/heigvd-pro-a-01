package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import connection.ClientRequest;
import exceptions.*;
import utils.CheckForm;
import utils.Crypto;
import java.io.IOException;
import utils.Regexp;

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
   private Label error;

   @FXML
   public void onRegisterClick() throws CustomException, IOException, ProtocolException {

      ClientRequest cr = new ClientRequest();

      String username = this.username.getText();
      String password = this.password.getText();
      String passwordConfirmed = this.passwordConfirmed.getText();
      String telegramUsername = this.telegramUsername.getText();

      if(!CheckForm.isAllNotEmpty(username, password, passwordConfirmed, telegramUsername)){
         error.setText("All fields are mandatory !");
         error.setVisible(true);
         return;
      }

      if(!password.equals(passwordConfirmed)) {
         error.setText("Password didn't match !");
         error.setVisible(true);
         return;
      }

      if(!CheckForm.isValid(password, Regexp.PASSWORD)) {
         error.setText("Password should have 8 characters, lowercase, uppercase, number and special chars");
         error.setVisible(true);
         return;
      }

      if(!CheckForm.isValid(telegramUsername, Regexp.PSEUDO_TELEGRAM)) {
         error.setText("Pseudo Telegram is not valid !");
         error.setVisible(true);
         return;
      }

      error.setVisible(false);
      cr.register(username, Crypto.sha512(password,SALT),telegramUsername);
   }
}
