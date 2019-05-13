package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import locale.I18N;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class APIRuleController implements Initializable {

   @FXML
   private Button newRule;

   private String serviceName = "";

   public void onNewRuleClick() {
      String serviceName = getServiceName();
      try {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/" + serviceName + "ServiceView.fxml"));
         fxmlLoader.setResources(ResourceBundle.getBundle("Internationalization", I18N.getLocale()));
         Parent root = fxmlLoader.load();
         Stage stage = new Stage();

         stage.setScene(new Scene(root));
         stage.setTitle("ASAPP - " + ResourceBundle.getBundle("Internationalization", I18N.getLocale()).getString(serviceName));
         stage.show();
      } catch (IOException e) {
         System.out.println("Failed to create new Window : " + e.getMessage());
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

   }

   public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
   }

   public String getServiceName() {
      return serviceName;
   }
}
