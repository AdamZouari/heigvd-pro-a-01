import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

   @FXML
   private Button btnTest;

   @FXML
   private void onClick() {
      if(btnTest.getText().equals("Coucou"))
         btnTest.setText("Test");
      else
         btnTest.setText("Coucou");
   }


}
