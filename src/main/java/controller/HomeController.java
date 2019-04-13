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

    @FXML
    private AnchorPane contentPane;

    private String ressourcesPath = "../";

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().add(loadFXML(ressourcesPath + "HomeFeedView.fxml"));
    }

    @FXML
    private void onHomeButtonClick() {
        changeContent("HomeFeedView.fxml");
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
        changeContent("SettingsView.fxml");
    }

    @FXML
    private void onTwitterButtonClick() {
        changeContent("AddRuleView.fxml");
    }

    @FXML
    private void onCFFButtonClick() {
        changeContent("AddRuleView.fxml");
    }

    @FXML
    private void onRTSButtonClick() {
        changeContent("AddRuleView.fxml");
    }

    @FXML
    private void onWeatherButtonClick() {
        changeContent("AddRuleView.fxml");
    }

    private Parent loadFXML(String name) {
        try {
            return FXMLLoader.load(getClass().getResource(name));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void changeContent(String viewName) {

        // TODO check if view is already loaded
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadFXML(ressourcesPath + viewName));
    }
}
