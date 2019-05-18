package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import locale.I18N;
import protocol.ExceptionCodes;
import utils.FormUtils;
import utils.Regexp;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button saveNameButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private FlowPane passwordChangePane;

    @FXML
    private TextField telegramTextField;

    @FXML
    private Button saveTelegramButton;

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
    private void onEditTelegramButtonClick() {
        saveTelegramButton.setVisible(true);
        telegramTextField.setEditable(true);
    }

    @FXML
    private void onSaveTelegramButtonClick() {
        if(!FormUtils.isValid(telegramTextField.getText(), Regexp.PSEUDO_TELEGRAM)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.INVALID_PSEUDO_TELEGRAM.getMessage());
            return;
        }

        telegramTextField.setEditable(false);
        saveTelegramButton.setVisible(false);

        FormUtils.hideErrorMessage(error);
    }

    @FXML
    private void onEditNameButtonClick() {
        saveNameButton.setVisible(true);
        nameTextField.setEditable(true);
    }

    @FXML
    private void onSaveNameButtonClick() {
        if(nameTextField.getText().isEmpty()) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.NAME_MISSING.getMessage());
            return;
        }

        nameTextField.setEditable(false);
        saveNameButton.setVisible(false);

        FormUtils.hideErrorMessage(error);
    }

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
        String currentLanguage = I18N.getLocale().getLanguage();

        if(currentLanguage.equals("en"))
            languageSelection.getSelectionModel().select("English");
        else
            languageSelection.getSelectionModel().select("Français");
    }

    // TODO : Change language in DB for user
    @FXML
    private void changeLanguage() {
        if (languageSelection.getSelectionModel().getSelectedItem().equals("English"))
            I18N.setLocale(I18N.EN);
        else
            I18N.setLocale(I18N.FR);
    }

    @FXML
    private void onSavePasswordClick() {
        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        String confirmedPass = confirmedPassword.getText();

        if(!FormUtils.isAllFilled(oldPass, newPass, confirmedPass)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.SOME_PASS_ARE_MISSING.getMessage());
            return;
        }

        if(!newPass.equals(confirmedPass)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORDS_DID_NOT_MATCH.getMessage());
            return;
        }

        if(!FormUtils.isValid(newPass, Regexp.PASSWORD)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.PASSWORD_INVALID.getMessage());
            return;
        }

        changePasswordsFormVisibility();
        FormUtils.hideErrorMessage(error);
    }

    private void cleanPasswordsInputs() {
        oldPassword.clear();
        newPassword.clear();
        confirmedPassword.clear();
    }

    private void changePasswordsFormVisibility() {
        if(passwordChangePane.isVisible()) {
            passwordChangePane.setVisible(false);
            cleanPasswordsInputs();
        } else {
            passwordChangePane.setVisible(true);
        }
    }
}
