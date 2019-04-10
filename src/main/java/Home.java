import Utils.TelegramNotification;
import javafx.application.Application;
import javafx.stage.Stage;
import service.ServiceCFF;
import service.ServiceMeteo;


public class Home  {

   public static void main(String[] args) {

      ServiceMeteo meteo = new ServiceMeteo();
      meteo.connect();
      System.out.println("Le temps a Lausanne :" + meteo.getWeather("Lausanne"));
      meteo.disconnect();

      ServiceCFF cff = new ServiceCFF();
      cff.connect();
      System.out.println("Voici toutes les connections possibles entre Lausanne et Geneve:");
      System.out.println(cff.getTrainsForPath("Lausanne","Geneve"));
      cff.disconnect();


      TelegramNotification notification = new TelegramNotification();
      notification.sendHelloWorld(TelegramNotification.PRO_CHAT_ID);
   }


}
