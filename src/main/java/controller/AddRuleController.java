package controller;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class AddRuleController implements Initializable {

   @FXML
   private ChoiceBox chooseAction;

   @FXML
   private ChoiceBox chooseCondition;

   @FXML
   private CheckBox addText;

   @FXML
   private TextArea additionalText;

   private Service service;

   @FXML
   private void onAddTextButtonClick(){
      if(addText.isSelected()) {
         additionalText.setEditable(true);
      } else {
         additionalText.setEditable(false);
      }
   }

   @FXML
   public void initialize(URL location, ResourceBundle resources) {
      // Initialiser le service selon le bouton appuyé --> comment on le récupère.
   }

   @FXML
   public void setChooseAction() {
      // Checker le nom du service qu'on utilise

      // Mettre à disposition la liste des actions
   }

   @FXML
   public void setChooseCondition() {
      // if (service meteo) { /* Mettre code ici */}

      // Mettre à disposition la liste des conditions : --> fait-il beau, fait-il chaud, fait-il plus de 20°C, y a t-il
      // de la pluie.

   }
}
