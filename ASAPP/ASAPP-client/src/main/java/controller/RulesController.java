package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RulesController implements Initializable {

    @FXML
    TextArea rulesContent;

    @FXML
    private void deleteAllRules(){
        System.out.println("DELETE ALL");
    }

    @FXML
    private void delete() {
        System.out.println("delete");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*ClientRequest cr = new ClientRequest();
        int ruleid = 42;
        try {
            cr.deleteUserRuleById(ruleid);
            rulesContent.setText(cr.getRulesContent());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CustomException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }*/
    }
}
