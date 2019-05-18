package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import connection.ClientRequest;
import exceptions.*;
import protocol.ExceptionCodes;
import utils.FormUtils;
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

      if(!FormUtils.isAllFilled(username, password, passwordConfirmed, telegramUsername)){
         FormUtils.displayErrorMessage(error, ExceptionCodes.ALL_FIELDS_ARE_NOT_FILLED.getMessage());
         return;
      }

      if(!password.equals(passwordConfirmed)) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORDS_DID_NOT_MATCH.getMessage());
         return;
      }

      if(!FormUtils.isValid(password, Regexp.PASSWORD)) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORD_INVALID.getMessage());
         return;
      }

      if(!FormUtils.isValid(telegramUsername, Regexp.PSEUDO_TELEGRAM)) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.INVALID_PSEUDO_TELEGRAM.getMessage());
         return;
      }

      FormUtils.hideErrorMessage(error);
      cr.register(username, Crypto.sha512(password,SALT),telegramUsername);

      // TODO : Afficher les erreurs du serveurs
   }
}
