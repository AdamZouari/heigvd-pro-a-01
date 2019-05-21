package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import utils.JsonParserCFF;
import utils.JsonParserRules;
import protocol.ExceptionCodes;
import utils.FormUtils;
import utils.Regexp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CFFServiceController implements Initializable {

    @FXML
    private CheckBox menuCheckBox;

    @FXML
    private CheckBox telegramCheckBox;

    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @FXML
    private TextField departureTime;

    @FXML
    private TextField requestTime;

    @FXML
    private CheckBox disruptionCheckBox;

    @FXML
    private Label error;

    @FXML
    private void onAddRuleClick() throws ProtocolException, CustomException, IOException {
        boolean menu = menuCheckBox.isSelected();
        boolean telegram = telegramCheckBox.isSelected();

        String from = this.from.getText();
        String to = this.to.getText();
        String departureTime = this.departureTime.getText();
        String arrivalTime = this.requestTime.getText();
        boolean telegramNotif = telegramCheckBox.isSelected();
        boolean disruptionNotif = disruptionCheckBox.isSelected();
        boolean menuNotif = this.menuCheckBox.isSelected();


        if(!menu && !telegram) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            return;
        }

        if(!FormUtils.isAllFilled(from, to, departureTime, arrivalTime)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.ALL_FIELDS_ARE_NOT_FILLED.getMessage());
            return;
        }

        if(!FormUtils.isValid(from, Regexp.CITY)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.DEPARTURE_IS_NOT_A_CITY.getMessage());
            return;
        }

        if(!FormUtils.isValid(to, Regexp.CITY)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.ARRIVAL_IS_NOT_A_CITY.getMessage());
            return;
        }

        if(!FormUtils.isValid(departureTime, Regexp.TIME)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.DEPARTURE_IS_NOT_IN_TIME_FORMAT.getMessage());
            return;
        }

        if(!FormUtils.isValid(arrivalTime, Regexp.TIME)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_HOUR_IS_NOT_IN_TIME_FORMAT.getMessage());
            return;
        }

        ClientRequest cr = new ClientRequest();

        // specify on server looping each day and compare one hour before the departureTime of train
        // and actual date and notify to the user telegram id
        // Entities.Rule rule = new Entities.CffRule("","","","24","");
        //
        JSONObject jsonToSend = null; // parse to create a json

        jsonToSend = JsonParserRules.createCffRuleJson( from,  to,  departureTime,  arrivalTime,  telegramNotif, menuNotif,
         disruptionNotif);

        // TODO we need to parse to create a json
        cr.addRule(jsonToSend.toString());


        // TODO once json as string stored in DB, then transform from string (then JsonObject then finally to rule)
        error.setVisible(false);

        ((Stage) this.from.getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
