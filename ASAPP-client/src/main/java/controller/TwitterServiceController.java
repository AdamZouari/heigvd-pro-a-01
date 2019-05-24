package controller;

import connection.ClientRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import protocol.ExceptionCodes;
import utils.FormUtils;
import utils.JsonParserRules;
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
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            return;
        }

        if(!FormUtils.isValid(twitter, Regexp.PSEUDO_TWITTER)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.INVALID_TWITTER.getMessage());
            return;
        }

        JSONObject json = JsonParserRules.createTwitterRuleJson(twitter, menu, telegram);
        try {
            new ClientRequest().addRule(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((Stage) this.twitterId.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
