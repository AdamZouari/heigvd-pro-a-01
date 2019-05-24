package controller;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRuleController implements Initializable {

   @FXML
   private CheckBox addText;

   @FXML
   private TextArea additionalText;

   @FXML
   private void onAddTextButtonClick(){
      if(addText.isSelected()) {
         additionalText.setDisable(false);
      } else {
         additionalText.setText("");
         additionalText.setDisable(true);
      }
   }

   @FXML
   public void initialize(URL location, ResourceBundle resources) {
   }
}