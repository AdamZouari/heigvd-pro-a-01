import main.java.Utils.TelegramNotification;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import service.ServiceMeteo;
import service.ServiceCFF;
import Utils.*;

import java.io.FileNotFoundException;

public class Home  {

   public static void main(String[] args) throws FileNotFoundException, ParseException {

      ServiceMeteo meteo = new ServiceMeteo();
      meteo.connect();
      System.out.println("Le temps a Lausanne :" + meteo.getWeather("Lausanne"));
      meteo.disconnect();

      ServiceCFF cff = new ServiceCFF();
      cff.connect();
      System.out.println("Voici toutes les connexions possibles entre Lausanne et Geneve:");
      String connectionsLtoG = cff.getTrainsForPath("Lausanne","Geneve","2012-03-25","17:30")
      System.out.println(connectionsLtoG);

      JsonParserCFF.parseCFF(connectionsLtoG);


      cff.disconnect();


      TelegramNotification notification = new TelegramNotification();
      notification.sendHelloWorld(TelegramNotification.PRO_CHAT_ID);
   }


}
