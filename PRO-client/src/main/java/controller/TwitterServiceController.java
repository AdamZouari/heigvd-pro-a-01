package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import protocol.ExceptionCodes;
import utils.CheckForm;
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
    private void onAddRuleClick() {
        boolean menu = menuCheckBox.isSelected();
        boolean telegram = telegramCheckBox.isSelected();

        String twitter = twitterId.getText();

        if(!menu && !telegram) {
            error.setText(ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            error.setVisible(true);
            return;
        }

        if(!CheckForm.isValid(twitter, Regexp.PSEUDO_TELEGRAM)) {
            error.setText(ExceptionCodes.INVALID_PSEUDO_TELEGRAM.getMessage());
            error.setVisible(true);
            return;
        }

        error.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
