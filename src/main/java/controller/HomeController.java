package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private boolean nightMode = false;

    private String ressourcesPath = "../";

    @FXML
    private void onHomeButtonClick() {
        // TODO
    }

    @FXML
    private void onRulesListButtonClick() {
        // TODO
    }

    @FXML
    private void onThemeToggleClick() {
        // TODO implement
    }

    @FXML
    private void onSettingsButtonClick() {
        // TODO implement
    }

    @FXML
    private void onTwitterButtonClick() {
        // TODO implement
    }

    @FXML
    private void onCFFButtonClick() {
        // TODO implement
    }

    @FXML
    private void onRTSButtonClick() {
        // TODO implement
    }

    @FXML
    private void onWeatherButtonClick() {
        // TODO implement
    }

    @FXML
    private AnchorPane contentPane;

    private Parent loadFXML(String name) {
        try {
            return FXMLLoader.load(getClass().getResource(name));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().add(loadFXML(ressourcesPath + "AddRuleView.fxml"));
    }
}
