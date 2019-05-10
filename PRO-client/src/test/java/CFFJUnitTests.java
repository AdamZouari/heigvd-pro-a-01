import utils.JsonParserCFF;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import service.ServiceCFF;


import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CFFJUnitTests {


    @Test
    public void connnectionTOAPIisOK(){

        //assert(cff.connect(),"");
    }

    @Test
    public void testCFFConnectionsBetweenTwoCities() throws FileNotFoundException, ParseException {
        ServiceCFF cff = new ServiceCFF();
        cff.connect();

        System.out.println("Voici toutes les connexions possibles entre Lausanne et Geneve:");
        String connectionsLtoG = cff.getTrainsForPath("Lausanne","Geneve","2019-05-09","17:30");
        System.out.println(connectionsLtoG);

        System.out.println(JsonParserCFF.parseCFF(connectionsLtoG));

        cff.disconnect();
    }


    @Test
    public void testCFFModificationOfTrain() throws FileNotFoundException, ParseException {
        String cff = new ServiceCFF().getTrainsForPath("Lausanne","Geneve","2019-05-09","17:30");
        System.out.println(JsonParserCFF.parseCFF(cff));

    }
}
