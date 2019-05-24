package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import locale.I18N;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ToggleSwitch mode;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TitledPane contentTitle;

    private String resourcesPath = "/";

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().add(loadFXML(resourcesPath + "HomeFeedView.fxml"));
    }

    @FXML
    private void onHomeButtonClick() {
        setContentTitle("home");
        changeContent("HomeFeedView.fxml");
    }

    @FXML
    private void onRulesListButtonClick() {
        setContentTitle("activeRules");
        changeContent("APIRuleView.fxml", "Rules");
    }

    @FXML
    private void onSettingsButtonClick() {
        setContentTitle("settings");
        changeContent("SettingsView.fxml");
    }

    @FXML
    private void onTwitterButtonClick() {
        setContentTitle("Twitter");
        changeContent("APIRuleView.fxml", "Twitter");
    }

    @FXML
    private void onCFFButtonClick() {
        setContentTitle("CFF");
        changeContent("APIRuleView.fxml", "CFF");
    }

    @FXML
    private void onRTSButtonClick() {
        setContentTitle("RTS");
        changeContent("APIRuleView.fxml", "RTS");
    }

    @FXML
    private void onWeatherButtonClick() {
        setContentTitle("Weather");
        changeContent("APIRuleView.fxml", "Weather");
    }

    private Parent loadFXML(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
            return fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Parent loadFXML(String name, String serviceName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
            Parent content = fxmlLoader.load();
            APIRuleController controller = fxmlLoader.getController();
            controller.setServiceName(serviceName);

            return content;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void setContentTitle(String title) {
            contentTitle.setText(ResourceBundle.getBundle("Internationalization",
                    I18N.getLocale()).getString(title));

    }

    private void changeContent(String viewName) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadFXML(resourcesPath + viewName));
    }

    private void changeContent(String viewName, String serviceName) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loadFXML(resourcesPath + viewName, serviceName));
    }
}
