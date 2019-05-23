package controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import locale.I18N;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class APIRuleController implements Initializable {
   @FXML
   private AnchorPane anchor;

   @FXML
   private FlowPane tablesPane;

   @FXML
   private FlowPane buttonsPane;

   @FXML
   private Button newRule;

   private String serviceName = "";

   private ResourceBundle resource;

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
   }

   void setServiceName(String serviceName) {
      this.serviceName = serviceName;

      switch(serviceName) {
         case "CFF":
            addCFFTable();
            break;

         case "Twitter":
            addTwitterTable();
            break;

         case "Weather":
            addWeatherTable();
            break;

         case "RTS":
            addRTSTable();
            break;

         default:
            Button deleteAll = new Button(resource.getString("deleteAllRules"));

            buttonsPane.getChildren().remove(newRule);
            buttonsPane.getChildren().add(deleteAll);

            anchor.setPrefHeight(4*420 + 50);

            addCFFTable();
            addRTSTable();
            addTwitterTable();
            addWeatherTable();
            break;
      }
   }

   String getServiceName() {
      return serviceName;
   }

   private void addCFFTable() {
      TableView table = new TableView();

      TableColumn from = new TableColumn("De");
      TableColumn to = new TableColumn("A");
      TableColumn at = new TableColumn("Dép.");
      TableColumn arrived = new TableColumn("Arr.");
      TableColumn menu = new TableColumn("Menu");
      TableColumn telegram = new TableColumn("Telegram");
      TableColumn delay = new TableColumn("Retard");
      TableColumn delete = new TableColumn("Supp");

      // width = 550
      from.setPrefWidth(85);
      to.setPrefWidth(85);
      at.setPrefWidth(70);
      arrived.setPrefWidth(70);
      menu.setPrefWidth(60);
      telegram.setPrefWidth(60);
      delay.setPrefWidth(60);
      delete.setPrefWidth(60);

      table.getColumns().addAll(from, to, at, arrived, menu, telegram, delay, delete);

      FlowPane flow = new FlowPane();
      flow.getChildren().add(table);
      tablesPane.getChildren().add(flow);
   }

   private void addTwitterTable() {
      TableView table = new TableView();

      TableColumn twitter = new TableColumn("Twitter");
      TableColumn menu = new TableColumn("Menu");
      TableColumn telegram = new TableColumn("Telegram");
      TableColumn delete = new TableColumn("Supp");

      twitter.setPrefWidth(370);
      menu.setPrefWidth(60);
      telegram.setPrefWidth(60);
      delete.setPrefWidth(60);

      table.getColumns().addAll(twitter, menu, telegram, delete);

      FlowPane flow = new FlowPane();
      flow.getChildren().add(table);
      tablesPane.getChildren().add(flow);
   }

   private void addWeatherTable() {
      TableView table = new TableView();

      TableColumn type = new TableColumn("Weather");
      TableColumn tempSel = new TableColumn("Condition");
      TableColumn temperature = new TableColumn("Temp.");
      TableColumn city = new TableColumn("Ville");
      TableColumn time = new TableColumn("Heure");
      TableColumn menu = new TableColumn("Menu");
      TableColumn telegram = new TableColumn("Telegram");
      TableColumn delete = new TableColumn("Supp");

      type.setPrefWidth(95);
      tempSel.setPrefWidth(60);
      temperature.setPrefWidth(50);
      city.setPrefWidth(105);
      time.setPrefWidth(60);
      menu.setPrefWidth(60);
      telegram.setPrefWidth(60);
      delete.setPrefWidth(60);

      table.getColumns().addAll(type, tempSel, temperature, city, time, menu, telegram, delete);

      FlowPane flow = new FlowPane();
      flow.getChildren().add(table);
      tablesPane.getChildren().add(flow);
   }

   private void addRTSTable() {
      TableView table = new TableView();

      TableColumn channel = new TableColumn("Chaine");
      TableColumn time = new TableColumn("Heure");
      TableColumn menu = new TableColumn("Menu");
      TableColumn telegram = new TableColumn("Telegram");
      TableColumn delete = new TableColumn("Supp");

      channel.setPrefWidth(310);
      time.setPrefWidth(60);
      menu.setPrefWidth(60);
      telegram.setPrefWidth(60);
      delete.setPrefWidth(60);

      table.getColumns().addAll(channel, time, menu, telegram, delete);

      FlowPane flow = new FlowPane();
      flow.getChildren().add(table);
      tablesPane.getChildren().add(flow);
   }
}
