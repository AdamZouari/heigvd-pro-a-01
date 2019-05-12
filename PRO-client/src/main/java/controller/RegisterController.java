package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import connection.ClientRequest;
import exceptions.*;
import utils.CheckForm;
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

      String username = this.username.getText();
      String password = this.password.getText();
      String passwordConfirmed = this.passwordConfirmed.getText();
      String telegramUsername = this.telegramUsername.getText();

      if(!CheckForm.isAllNotEmpty(username, password, passwordConfirmed, telegramUsername)){
         System.out.println("Fields");
//         throw new CustomException("All fields are mandatory");
      }

      // TODO : syntaxe du mot de passe
      if(!password.equals(passwordConfirmed)) {
         System.out.println("Pass");
//         throw new CustomException("Password didn't match");
      }

      if(!CheckForm.isValid(telegramUsername, "([A-Za-z0-9]|_){5,32}")) {
         System.out.println("Telegram");
         throw new CustomException("Pseudo Telegram is not valid");
      }

      cr.register(username, Crypto.sha512(password,SALT),telegramUsername);
   }
}
