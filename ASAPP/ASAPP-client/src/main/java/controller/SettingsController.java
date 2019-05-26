package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import locale.I18N;
import protocol.ExceptionCodes;
import utils.FormUtils;
import utils.Regexp;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private FlowPane passwordChangePane;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField confirmedPassword;

    @FXML
    private ChoiceBox languageSelection;

    @FXML
    private Label error;

    @FXML
    private Label language;

    @FXML
    private Button passwordChangeButton;

    @FXML
    private Label oldPasswordLabel;

    @FXML
    private Label newPasswordLabel;

    @FXML
    private Label confirmPass;

    @FXML
    private Button savePasswordButton;

    @FXML
    private Button cancelPasswordButton;

    @FXML
    private void onPasswordChangeClick() {
        changePasswordsFormVisibility();
    }

    @FXML
    private void onCancelPasswordButtonClick() {
        changePasswordsFormVisibility();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        ClientRequest cr = new ClientRequest();
        try {
            String currentLanguage = cr.getLanguage();

            // sets up the langue as defined in the database
            if (currentLanguage.equals("EN"))
                languageSelection.getSelectionModel().select("English");
            else
                languageSelection.getSelectionModel().select("Français");

        } catch (IOException | ProtocolException | CustomException e) {
            FormUtils.displayErrorMessage(error, e.getMessage());
        }
    }

    @FXML
    private void changeLanguage() {
        try {
            //  Change language in actual window
            if (languageSelection.getSelectionModel().getSelectedItem().equals("English")) {
                I18N.setLocale(I18N.EN);
            } else{
                I18N.setLocale(I18N.FR);
            }

            //  Change language in DB for user
            ClientRequest cr = new ClientRequest();
            cr.updateLanguage(I18N.getLocale().getCountry());

            changeDisplayedLanguage();
            FormUtils.hideErrorMessage(error);
        } catch (IOException | ProtocolException | CustomException e) {
            FormUtils.displayErrorMessage(error, e.getMessage());
        }
    }

    @FXML
    private void onSavePasswordClick() {
        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        String confirmedPass = confirmedPassword.getText();

        if (!FormUtils.isAllFilled(oldPass, newPass, confirmedPass)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.SOME_PASS_ARE_MISSING.getMessage());
            return;
        }

        if (!newPass.equals(confirmedPass)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORDS_DID_NOT_MATCH.getMessage());
            return;
        }

        if (!FormUtils.isValid(newPass, Regexp.PASSWORD)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORD_INVALID.getMessage());
            return;
        }

        ClientRequest cr = new ClientRequest();

        try {
            cr.updatePassword(confirmedPass);
        } catch (IOException | CustomException | ProtocolException e) {
            FormUtils.displayErrorMessage(error, e.getMessage());

        }

        changePasswordsFormVisibility();
        //FormUtils.hideErrorMessage(error);

    }

    private void cleanPasswordsInputs() {
        oldPassword.clear();
        newPassword.clear();
        confirmedPassword.clear();
    }

    private void changePasswordsFormVisibility() {
        if (passwordChangePane.isVisible()) {
            passwordChangePane.setVisible(false);
            cleanPasswordsInputs();
        } else {
            passwordChangePane.setVisible(true);
        }
    }

    private void changeDisplayedLanguage() {
        ResourceBundle resource = ResourceBundle.getBundle("Internationalization", I18N.getLocale());

        language.setText(resource.getString("language"));

        passwordChangeButton.setText(resource.getString("changePassword"));

        oldPasswordLabel.setText(resource.getString("oldPassword"));
        newPasswordLabel.setText(resource.getString("newPassword"));
        confirmPass.setText(resource.getString("confirmation"));
        savePasswordButton.setText(resource.getString("save"));
        cancelPasswordButton.setText(resource.getString("cancel"));
    }
}
