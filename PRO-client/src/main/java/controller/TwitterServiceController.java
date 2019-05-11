package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
    private void onTelegramClick() {
        // TODO
    }

    @FXML
    private void onAddRuleClick() {
        // TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
