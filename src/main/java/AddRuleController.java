import javafx.fxml.FXML;

import javafx.scene.control.*;

public class AddRuleController {
   @FXML
   private CheckBox addText;

   @FXML
   private TextArea additionalText;

   @FXML
   private void onAddTextButtonClick(){
      if(addText.isSelected())
         additionalText.setEditable(true);
      else
         additionalText.setEditable(false);
   }
}
