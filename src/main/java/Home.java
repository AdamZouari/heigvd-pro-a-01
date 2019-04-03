import javafx.application.Application;
import javafx.stage.Stage;


public class Home  {

   public static void main(String[] args) {

      ServiceMeteo meteo = new ServiceMeteo();
      meteo.connect();
      System.out.println("Le temps a Lausanne :" + meteo.getWeather("Lausanne"));
      meteo.disconnect();
   }


}
