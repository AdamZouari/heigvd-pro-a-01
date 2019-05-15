package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Label error;

    @FXML
    private void onAddRuleClick() throws ProtocolException, CustomException, IOException {
        boolean menu = menuCheckBox.isSelected();
        boolean telegram = telegramCheckBox.isSelected();

        String from = this.from.getText();
        String to = this.to.getText();
        String departureTime = this.departureTime.getText();
        String requestTime = this.requestTime.getText();

        if(!menu && !telegram) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
            return;
        }

        if(!FormUtils.isAllFilled(from, to, departureTime, requestTime)) {
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

        if(!FormUtils.isValid(requestTime, Regexp.TIME)) {
            FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_HOUR_IS_NOT_IN_TIME_FORMAT.getMessage());
            return;
        }

        // TODO : Afficher les erreurs du serveur

        ClientRequest cr = new ClientRequest();

        cr.getCFF(from,to,departureTime,requestTime);
        if(telegram){
            // TODO specify on server looping each day and compare one hour before the departureTime of train
            // and actual date and notify to the user telegram id
        }

        error.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
