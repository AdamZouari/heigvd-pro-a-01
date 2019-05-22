import org.junit.jupiter.api.Test;
import service.ServiceCFF;
import service.ServiceMeteo;
import utils.JsonParserCFF;
import utils.TelegramNotification;

import java.io.FileNotFoundException;

public class CFFJUnitTests {


    @Test
    public void connnectionTOAPIisOK(){

        //assert(cff.connect(),"");
    }

    @Test
    public void testCFFConnectionsBetweenTwoCities() throws FileNotFoundException{
        ServiceCFF cff = new ServiceCFF();
        cff.connect();

        System.out.println("Voici toutes les connexions possibles entre Lausanne et Geneve:");
        String connectionsLtoG = cff.getTrainsForPath("laUsanne","genEve","2019-05-09","17:30");
        System.out.println(connectionsLtoG);

        System.out.println(JsonParserCFF.parseCFF(connectionsLtoG,"laUsanne","genEve"));

        cff.disconnect();
    }


    @Test
    public void testCFFModificationOfTrain()  {
        String cff = new ServiceCFF().getTrainsForPath("Lausanne","Geneve","2019-05-09","17:30");
        System.out.println(JsonParserCFF.parseCFForDelay(cff,"Lausanne","Geneve"));

    }


    @Test
    public void testIfCffNotifIsSendToUserTelegram(){
        //String cff = new ServiceCFF().getTrainsForPath("Lausanne","Geneve","2019-05-09","17:30");
        TelegramNotification t = new TelegramNotification();
        t.sendRuleResult("142772696",  Integer.toString(new ServiceMeteo().getTemperature("Lausanne")));
    }
}
