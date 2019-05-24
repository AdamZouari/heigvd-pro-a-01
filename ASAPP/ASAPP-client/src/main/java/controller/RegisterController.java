package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import connection.ClientRequest;
import exceptions.*;
import javafx.stage.Stage;
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

        if (!FormUtils.isAllFilled(username, password, passwordConfirmed, telegramUsername)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.ALL_FIELDS_ARE_NOT_FILLED.getMessage());
            return;
        }

        if (!password.equals(passwordConfirmed)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORDS_DID_NOT_MATCH.getMessage());
            return;
        }

      if(!FormUtils.isValid(password, Regexp.PASSWORD)) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORD_INVALID.getMessage());
         return;
      }

        if (!FormUtils.isValid(telegramUsername, Regexp.PSEUDO_TELEGRAM)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.INVALID_PSEUDO_TELEGRAM.getMessage());
            return;
        }


        try {
            cr.addBot();

            // laisse un peu de temps a l'utilisater d'ajouter le bot (5 secondes)
            Thread.sleep(5000);

            // retourne le json des personnes qui ont ajouté le bot dans les dernieres 24h
            String json = cr.getJsonTelegram();

            int idTelegram = cr.getIdFromTelegramPseudo(json);

            cr.register(username, Crypto.sha512(password, SALT), telegramUsername, idTelegram);

            // Close current window
            ((Stage) this.username.getScene().getWindow()).close();
        } catch (CustomException | ProtocolException e) {
            FormUtils.displayErrorMessage(error, e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
