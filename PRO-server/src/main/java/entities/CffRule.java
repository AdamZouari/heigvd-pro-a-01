package entities;

import database.DatabaseController;
import org.json.JSONObject;
import service.ServiceCFF;
import utils.JsonParserCFF;
import utils.TelegramNotification;

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
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public String execute() {

        // To Do
        ServiceCFF cff = new ServiceCFF();
        cff.connect();

        String connections = cff.getTrainsForPath(from,to,departureTime,arrivalTime);

        // Here we parse the response from the server to show

        cff.disconnect();

        if(telegramNotif){
            // TODO choper le telegram id et envoyer via le bot
            // retard

            // TODO HELP ANTOINE
            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());

            //TODO WTF PK CA ENVOIE PAS DE NOTIF TELEGRAM EXACTEMENT PAREIL QUE POUR METEO....
            //System.out.println("TELEGRAM ID = " + telegramId + "\n CONNECTIONS : " + connections);
            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId, connections);
        }
        return JsonParserCFF.parseCFF(connections,from,to);


    }
}
