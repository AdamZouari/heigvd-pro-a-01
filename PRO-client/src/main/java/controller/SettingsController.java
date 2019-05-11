package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.net.URL;
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
    private void onEditTelegramButtonClick() {
        saveTelegramButton.setVisible(true);
        telegramTextField.setEditable(true);
    }

    @FXML
    private void onSaveTelegramButtonClick() {
        // TODO check input
        // change name in db etc...
        telegramTextField.setEditable(false);
        saveTelegramButton.setVisible(false);
    }

    @FXML
    private void onEditNameButtonClick() {
        saveNameButton.setVisible(true);
        nameTextField.setEditable(true);
    }

    @FXML
    private void onSaveNameButtonClick() {
        // TODO check input
        // change name in db etc...
        nameTextField.setEditable(false);
        saveNameButton.setVisible(false);
    }

    @FXML
    private void onPasswordChangeNameClick() {
        if(passwordChangePane.isVisible()) {
            passwordChangePane.setVisible(false);
            // TODO clean inputs

        } else {
            passwordChangePane.setVisible(true);
        }

    }

    @FXML
    private void onCancelPasswordButtonClick() {
        // TODO clean inputs
        passwordChangePane.setVisible(false);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }
}
