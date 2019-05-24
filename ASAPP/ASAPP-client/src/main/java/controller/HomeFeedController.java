package controller;

import connection.ClientRequest;
import exceptions.CustomException;
import exceptions.ProtocolException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeFeedController implements Initializable{

    private Accordion accordion = new Accordion();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ClientRequest cr = new ClientRequest();

//        try {
//            rulesResult.setText(cr.getRulesResult());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CustomException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        }
    }

    private void createCFFTitledPane() {
        TitledPane titledPane = new TitledPane();
        // TODO : implement

        accordion.getPanes().add(titledPane);
    }

    private void createWeatherTitledPane() {
        TitledPane titledPane = new TitledPane();
        // TODO : implement

        accordion.getPanes().add(titledPane);
    }

    private void createTwitterTitledPane() {
        TitledPane titledPane = new TitledPane();
        // TODO : implement

        accordion.getPanes().add(titledPane);
    }

    private void createRTSTitledPane() {
        TitledPane titledPane = new TitledPane();
        // TODO : implement

        accordion.getPanes().add(titledPane);
    }
}
