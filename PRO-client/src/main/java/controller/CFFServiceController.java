package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
    private void onTelegramClick() {
        // TODO

    }

    @FXML
    private void onAddRuleClick() throws ProtocolException, CustomException, IOException {
        // TODO

        String from = this.from.getText();
        String to = this.to.getText();
        String departureTime = this.departureTime.getText();
        String requestTime = this.requestTime.getText();


        ClientRequest cr = new ClientRequest();

        cr.getCFF(from,to,departureTime,requestTime);
        if(telegramCheckBox.isSelected()){
            // TODO specify on server looping each day and compare one hour before the departureTime of train
            // and actual date and notify to the user telegram id
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
