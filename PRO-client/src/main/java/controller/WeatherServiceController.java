package controller;

import connection.ClientRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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

public class WeatherServiceController implements Initializable {

    @FXML
    private CheckBox menuCheckBox;

    @FXML
    private CheckBox telegramCheckBox;

    @FXML
    private TextField location;

    @FXML
    private ChoiceBox weatherTypeSelection;

    @FXML
    private ChoiceBox temperatureSelection;

    @FXML
    private TextField temperature;

    @FXML
    private TextField time;

    @FXML
    private Label error;


    @FXML
    private void onAddRuleClick() {

        boolean menu = menuCheckBox.isSelected();
        boolean telegram = telegramCheckBox.isSelected();
        boolean isTempSelected = !(temperatureSelection.getSelectionModel().isEmpty());
        boolean isWeatherSelected = !(weatherTypeSelection.getSelectionModel().isEmpty());

        if(!menu && !telegram) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            return;
        }

        String location = this.location.getText();
        String time = this.time.getText();

        if(!FormUtils.isAllFilled(location, time)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.LOCATION_AND_TIME_MISSING.getMessage());
            return;
        }

        if(!FormUtils.isValid(location, Regexp.CITY)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.LOCATION_IS_NOT_A_CITY.getMessage());
            return;
        }


        if(!isTempSelected && !isWeatherSelected ) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.WEATHER_TYPE_OR_TEMPERATURE_CONDITION.getMessage());
            return;
        }

        String temperatureValue = this.temperature.getText();

        if(isTempSelected) {
            if(temperatureValue.isEmpty()){
                FormUtils.displayErrorMessage(error, ExceptionCodes.TEMPERATURE_MISSING.getMessage());
                return;
            }

            if(!FormUtils.isValid(temperatureValue, Regexp.NUMBER)){
                FormUtils.displayErrorMessage(error, ExceptionCodes.NOT_A_NUMBER.getMessage());
                return;
            }
        }

        if(!FormUtils.isValid(time, Regexp.TIME)){
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_HOUR_IS_NOT_IN_TIME_FORMAT.getMessage());
            return;
        }

        FormUtils.hideErrorMessage(error);

        String tempSelec;
        String weatherSelec;

        //TODO Checker car retourne NullPointerException ..
        // Permettre de laisser des cases sans les valider
        if (weatherTypeSelection.getValue() == null) {
            weatherSelec = "null";
        } else {
            weatherSelec = weatherTypeSelection.getValue().toString();
        }

        if (temperatureValue == null || temperatureSelection.getValue() == null) {
            tempSelec = "null";
            temperatureValue = "null";
        } else {
            tempSelec = temperatureSelection.getValue().toString();
        }

        JSONObject json = JsonParserRules.createMeteoRuleJson(telegram, menu, time, location, weatherSelec,
                                                              temperatureValue, tempSelec);

        // Send Rules to server;
        try {
            new ClientRequest().addRule(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close Window
        ((Stage)this.time.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        weatherTypeSelection.getItems().addAll("Ensoleillé", "Pluvieux", "Nuageux", "Neigeux");
        temperatureSelection.getItems().addAll("<",">");
    }
}