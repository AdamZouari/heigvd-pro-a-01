package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeFeedController implements Initializable{
    @FXML
    private Label ruleResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ClientRequest cr = new ClientRequest();
        try {
            ruleResult.setText(cr.getRulesResult());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CustomException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }
}
