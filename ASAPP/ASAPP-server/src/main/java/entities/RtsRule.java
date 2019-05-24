package entities;

import database.DatabaseController;
import org.json.JSONObject;
import service.ServiceRTS;
import utils.TelegramNotification;

import java.util.ArrayList;

public class RtsRule extends Rule {

    private static final String TAG = "RTS";
    private String channel, requestTime;

    /**
     * @param id
     * @param startDate
     **/
    public RtsRule(int id,String username, String startDate,String channel, String requestTime, boolean menuNotif,boolean telegramCheckBox) {
        super(id,username, TAG,startDate,menuNotif,telegramCheckBox);
        this.channel = channel;
        this.requestTime = requestTime;
    }

    @Override
    public int getPeriod() {
        return 24*60;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public String execute() {

        ServiceRTS service = new ServiceRTS();
        StringBuilder message = new StringBuilder();

        service.connect();

        message.append("Voici le programme du " + service.getDate(channel) + " pour la " + channel + " : \n \n");

        // On obtient les programmes du jour
        ArrayList<String> programme = service.getProgram(channel);
        programme.forEach((emission) -> {

            // La troisième occurence de ":" sépare l'heure et le nom de l'émission
            int firstOcc = emission.indexOf(':');
            int secondOcc = emission.indexOf(':', firstOcc + 1);
            int thirdOcc = emission.indexOf(':',secondOcc + 1);

            String horaire = emission.substring(firstOcc + 1, thirdOcc - 1);
            String titre = emission.substring(emission.indexOf('[') + 1, emission.indexOf(']'));
            message.append(horaire + " : " + titre + "\n");
        });

        // Notif Telegram
        if (telegramNotif) {
            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());
            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId, telegram.encodeMessageForURL(message.toString()));
        }
        return message.toString();
    }
}
