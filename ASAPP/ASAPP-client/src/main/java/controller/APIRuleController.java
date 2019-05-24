package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import locale.I18N;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class APIRuleController implements Initializable {
   @FXML
   private FlowPane pane;

   @FXML
   private FlowPane buttonsPane;

   @FXML
   private Button newRule;

   private String serviceName = "";

   private ResourceBundle resource;

   private String rules;

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
      resource = resourceBundle;

      ClientRequest cr = new ClientRequest();

//      try {
//         rules = cr.getRulesContent();
//      } catch (IOException | CustomException | ProtocolException e) {
//         e.printStackTrace();
//      }
   }

   void setServiceName(String serviceName) {
      this.serviceName = serviceName;

      switch(serviceName) {
         case "CFF":
            pane.getChildren().add(generateCFFAccordion());
            break;

         case "Twitter":
            break;

         case "Weather":
            break;

         case "RTS":
            break;

         default:
            Button deleteAll = new Button(resource.getString("deleteAllRules"));

            buttonsPane.getChildren().remove(newRule);
            buttonsPane.getChildren().add(deleteAll);

            TabPane tabs = new TabPane();
            Tab cff = new Tab("CFF");
            cff.setClosable(false);
            cff.setContent(generateCFFAccordion());

            Tab twitter = new Tab("Twitter");
            twitter.setClosable(false);

            Tab weather = new Tab(resource.getString("Weather"));
            weather.setClosable(false);

            Tab rts = new Tab("RTS");
            rts.setClosable(false);

            tabs.setPrefHeight(150);
            tabs.getTabs().addAll(cff, twitter, weather, rts);

            pane.getChildren().add(tabs);
            break;
      }
   }

   String getServiceName() {
      return serviceName;
   }

   private void deleteRule(int id, TitledPane titled, Accordion accordion) {
      accordion.getPanes().remove(titled);

      // TODO : Remove from DB rule with the id
   }

   private Accordion generateCFFAccordion() {
      Accordion accordion = new Accordion();

      TitledPane titledPane = new TitledPane();
      titledPane.setText("CFF 01");

      Label cities = new Label(resource.getString("from") + " ville1 " + resource.getString("to") + " ville2 ");
      FlowPane citiesPane = new FlowPane();
      citiesPane.getChildren().add(cities);

      Label hours = new Label(resource.getString("departureTime") + ": " + "heure, " + resource.getString("arrivalTime") + ": " + "heure");
      FlowPane hoursPane = new FlowPane();
      hoursPane.getChildren().add(hours);

      CheckBox menu = new CheckBox(resource.getString("inMenu"));
      menu.setSelected(true);
      menu.setDisable(false);

      CheckBox telegram = new CheckBox(resource.getString("onTelegram"));
      telegram.setSelected(false);
      telegram.setDisable(false);

      CheckBox disruption = new CheckBox(resource.getString("disruptionNotif"));
      disruption.setSelected(true);
      disruption.setDisable(false);

      FlowPane checkBoxes = new FlowPane();
      checkBoxes.getChildren().addAll(menu, telegram, disruption);

      Button delete = new Button(resource.getString("delete"));
      delete.setId(String.valueOf(1));
      delete.setOnAction(e -> deleteRule(Integer.parseInt(delete.getId()), titledPane, accordion));


      FlowPane fp = new FlowPane();
      fp.getChildren().addAll(citiesPane, hoursPane, checkBoxes, delete);
      titledPane.setContent(fp);

      accordion.getPanes().add(titledPane);

      return accordion;
   }

   private Accordion generateTwitterAccordion() {
      Accordion accordion = new Accordion();

      TitledPane titledPane = new TitledPane();
      titledPane.setText("Twitter 01");

      Label user = new Label(resource.getString("username") + ": " + "le super user twitter");

      Button delete = new Button(resource.getString("delete"));
      delete.setId(String.valueOf(1));
      delete.setOnAction(e -> deleteRule(Integer.parseInt(delete.getId()), titledPane, accordion));

//      FlowPane f


      return accordion;
   }

   private Accordion generateWeatherAccordion() {
      Accordion accordion = new Accordion();

      return accordion;
   }

   private Accordion generateRTSAccordion() {
      Accordion accordion = new Accordion();

      return accordion;
   }
}
