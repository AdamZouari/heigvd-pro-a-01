import static org.junit.jupiter.api.Assertions.assertEquals;

import Utils.JsonParserCFF;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import service.ServiceCFF;

import java.io.FileNotFoundException;

public class CFFJUnitTests {

    ServiceCFF cff = new ServiceCFF();
    @Test
    public void connnectionTOAPIisOK(){

        //assert(cff.connect(),"");
    }

    @Test
    public void testCFFConnectionsBetweenTwoCities() throws FileNotFoundException, ParseException {
        assertEquals(2,1+1);

        System.out.println("Voici toutes les connexions possibles entre Lausanne et Geneve:");
        String connectionsLtoG = cff.getTrainsForPath("Lausanne","Geneve","2012-03-25","17:30");
        System.out.println(connectionsLtoG);

        JsonParserCFF.parseCFF(connectionsLtoG);

        cff.disconnect();
    }
}
