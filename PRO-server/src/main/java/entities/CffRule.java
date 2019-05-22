package entities;

import service.ServiceCFF;
import utils.JsonParserCFF;

public class CffRule extends Rule{
    String from, to, departureTime, arrivalTime;
    private boolean disruptionNotif;
    private static final String TAG = "CFF";


    public CffRule(int id,String username, String startDate, String from, String to, String departureTime, String arrivalTime, boolean telegramNotif, boolean menuNotif,
                   boolean disruptionNotif) {
        super(id,username, TAG, startDate, menuNotif,telegramNotif);
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.disruptionNotif = disruptionNotif;
    }

    public String getTo() {
        return to;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }



    public boolean isDisruptionNotif() {
        return disruptionNotif;
    }

    public String getFrom() {

        return from;
    }


    @Override
    public String execute() {
        // To Do
        ServiceCFF cff = new ServiceCFF();
        cff.connect();

        String connections = cff.getTrainsForPath(from,to,departureTime,arrivalTime);

        // Here we parse the response from the server to show

        cff.disconnect();

        return JsonParserCFF.parseCFF(connections,from,to);

    }
}
