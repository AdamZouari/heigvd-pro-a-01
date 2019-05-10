package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ToggleSwitch mode;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TitledPane contentTitle;

    private String ressourcesPath = "../";

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().add(loadFXML(ressourcesPath + "HomeFeedView.fxml"));
    }

    @FXML
    private void onHomeButtonClick() {
        setContentTitle("Home");
        changeContent("HomeFeedView.fxml");
    }

    @FXML
    private void onRulesListButtonClick() {
        setContentTitle("Active rules");
        // TODO
    }

    @FXML
    private void onThemeToggleClick() {
        // TODO implement
    }

    @FXML
    private void onSettingsButtonClick() {
        setContentTitle("Settings");
        changeContent("SettingsView.fxml");
    }

    @FXML
    private void onTwitterButtonClick() {
        setContentTitle("Twitter");
        changeContent("AddRuleView.fxml");
    }

    @FXML
    private void onCFFButtonClick() {
        setContentTitle("CFF");
        changeContent("AddRuleView.fxml");
    }

    @FXML
    private void onRTSButtonClick() {
        setContentTitle("RTS");
        changeContent("AddRuleView.fxml");
    }

    @FXML
    private void onWeatherButtonClick() {
        setContentTitle("Weather");
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

    private void setContentTitle(String title) {
        contentTitle.setText(title);
    }

    private void changeContent(String viewName) {

        // TODO check if view is already loaded
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadFXML(ressourcesPath + viewName));
    }
}
