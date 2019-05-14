package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import protocol.ExceptionCodes;
import utils.CheckForm;

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
    private TextArea noteTextArea;

    @FXML
    private Label error;

    @FXML
    private void onAdditionalNoteClick() {
        if(noteTextArea.isEditable()) {
            noteTextArea.setEditable(false);
            noteTextArea.clear();
        } else {
            noteTextArea.setEditable(true);
        }
    }

    @FXML
    private void onAddRuleClick() {
        boolean menu = menuCheckBox.isSelected();
        boolean telegram = telegramCheckBox.isSelected();

        if(!menu && !telegram) {
            error.setText(ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            error.setVisible(true);
            return;
        }

        String location = this.location.getText();
        String time = this.time.getText();

        if(!CheckForm.isAllFilled(location, time)){
            error.setText(ExceptionCodes.LOCATION_AND_TIME_MISSING.getMessage());
            error.setVisible(true);
            return;
        }

        error.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}