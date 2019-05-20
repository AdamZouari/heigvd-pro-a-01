package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import utils.JsonParserCFF;
import utils.JsonParserRules;

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
        String arrivalTime = this.requestTime.getText();
        boolean telegramNotif;
        boolean disruptionNotif;
        boolean menuNotif = this.menuCheckBox.isSelected();

        telegramNotif = telegramCheckBox.isSelected();
        disruptionNotif = disruptionCheckBox.isSelected();

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

        // TODO Here we send the rule
        //cr.getCFF(from,to,departureTime,requestTime);


        // TODO once json as string stored in DB, then transform from string (then JsonObject then finally to rule)
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
