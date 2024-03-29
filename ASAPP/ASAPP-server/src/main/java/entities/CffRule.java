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
    public int getPeriod() {
        return 24*60;
    }

    @Override
    public int getInitialDelay() {
        String[] timeT = arrivalTime.split(":");

        int requestTime = (Integer.parseInt(timeT[0]) % 24)*60 + Integer.parseInt(timeT[1]);

        return getDelayFromRequestTime(requestTime);
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
        String result = JsonParserCFF.parseCFF(connections,from,to);

        int minDelay = cff.getDelay(from,to,departureTime,arrivalTime);

        // Here we parse the response from the server to show
        if (disruptionNotif &&  (minDelay > 0)) {

            String message = "Les connexions entre " + from + " et " + to + " ont un retard minimum de " + minDelay + " minutes minimum.";
            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());

            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId, telegram.encodeMessageForURL(message));
        }

        cff.disconnect();

        if (telegramNotif) {

            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());

            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId, telegram.encodeMessageForURL(result));
        }
        return result;
    }
}
