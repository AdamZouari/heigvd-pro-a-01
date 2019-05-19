package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import protocol.ExceptionCodes;
import utils.FormUtils;
import utils.Regexp;

import java.net.URL;
import java.util.ResourceBundle;

public class TwitterServiceController implements Initializable {

    @FXML
    private CheckBox menuCheckBox;

    @FXML
    private CheckBox telegramCheckBox;

    @FXML
    private TextField twitterId;

    @FXML
    private Label error;

    @FXML
    private TextField pin;

    @FXML
    private void onAddRuleClick() {
        boolean menu = menuCheckBox.isSelected();
        boolean telegram = telegramCheckBox.isSelected();

        String twitter = twitterId.getText();

        if(!menu && !telegram) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            return;
        }

        if(!FormUtils.isValid(twitter, Regexp.PSEUDO_TELEGRAM)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.INVALID_PSEUDO_TELEGRAM.getMessage());
            return;
        }

        FormUtils.hideErrorMessage(error);
    }

    @FXML
    private void onTwitterConnectionClick() {
        // TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
