package controller;

import connection.ClientRequest;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import protocol.ExceptionCodes;
import utils.FormUtils;
import utils.JsonParserRules;
import utils.Regexp;

public class RTSServiceController {

   @FXML
   private CheckBox menuCheckBox;

   @FXML
   private CheckBox telegramCheckBox;

   @FXML
   private ChoiceBox channel;

   @FXML
   private TextField requestTime;

   @FXML
   private Label error;

   @FXML
   private void onAddRuleClick() {
      boolean menu = menuCheckBox.isSelected();
      boolean telegram = telegramCheckBox.isSelected();

      if(!menu && !telegram) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_APPEARS_NOWHERE.getMessage());
         return;
      }

      String time = requestTime.getText();

      if(time.isEmpty() || channel.getSelectionModel().isEmpty()) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.ALL_FIELDS_ARE_NOT_FILLED.getMessage());
         return;
      }

      if(!FormUtils.isValid(time, Regexp.TIME)) {
         FormUtils.displayErrorMessage(error, ExceptionCodes.REQUEST_HOUR_IS_NOT_IN_TIME_FORMAT.getMessage());
         return;
      }

      JSONObject json = JsonParserRules.createRTSRuleJson(channel.getValue().toString(), requestTime.getText(), menu, telegram);

      try {
         new ClientRequest().addRule(json.toString());
      } catch (Exception e) {
         e.printStackTrace();
      }

      ((Stage) this.channel.getScene().getWindow()).close();


   }
}
