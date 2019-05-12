package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
   @FXML
   private void onSignUpClick() {
      try {
         Parent root = FXMLLoader.load(this.getClass().getResource("/RegisterView.fxml"));
         Stage stage = new Stage();

         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - Register");
         stage.show();
      } catch (IOException e) {
         System.out.println("Failed to create new Window : " + e.getMessage());
      }
   }

   @FXML
   private void onLoginButtonClick() {

   }
}