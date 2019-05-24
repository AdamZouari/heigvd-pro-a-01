package controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
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
    public void initialize(URL location, ResourceBundle resources) {}
}
