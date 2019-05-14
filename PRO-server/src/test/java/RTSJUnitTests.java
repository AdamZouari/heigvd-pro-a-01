import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import service.ServiceCFF;
import service.ServiceRTS;
import utils.JsonParserCFF;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RTSJUnitTests {

    @Test
    public void testRTSPRograms() throws FileNotFoundException, ParseException {
        ServiceRTS rts = new ServiceRTS();
        rts.connect();

        System.out.println("Voici tout les programmes :");
        ArrayList<String> programs = rts.getProgram();
        System.out.println(programs);

        //System.out.println(JsonParserRTS.parseRTS(connectionsLtoG,"laUsanne","genEve"));

        rts.disconnect();
    }


    @Test
    public void testCFFModificationOfTrain() throws FileNotFoundException, ParseException {
        String cff = new ServiceCFF().getTrainsForPath("Lausanne","Geneve","2019-05-09","17:30");
        System.out.println(JsonParserCFF.parseCFForDelay(cff,"Lausanne","Geneve"));

    }
}
