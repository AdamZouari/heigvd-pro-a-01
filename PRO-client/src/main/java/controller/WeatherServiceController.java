package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private Button addRuleButton;

    @FXML
    private void onTelegramClick() {
        // TODO check if telegram is linked etc...
    }

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
        // TODO check inputs, process and clear
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
