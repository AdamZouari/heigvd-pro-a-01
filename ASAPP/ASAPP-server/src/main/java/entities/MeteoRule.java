package entities;

import database.DatabaseController;
import org.json.JSONObject;
import service.ServiceMeteo;
import utils.TelegramNotification;

public class MeteoRule extends Rule {

    private static final String TAG = "METEO";
    private String location, weatherType, temperature, temperatureSelection;
    /**
     *
     * @param id
     **/

    public MeteoRule(int id,String username, String startDate, boolean telegramNotif, boolean menuNotif, String location,
                     String weatherType, String temperature, String temperatureSelection)
    {
        super(id,username, TAG, startDate, menuNotif,telegramNotif);
        this.location = location;
        this.weatherType = weatherType;
        this.temperature = temperature;
        this.temperatureSelection = temperatureSelection;

    }

    public MeteoRule(JSONObject json) {
        super(json);
        location = json.get("location").toString();
        weatherType = json.get("weatherType").toString();
        temperature = json.get("temperature").toString();
        temperatureSelection = json.get("temperatureSelection").toString();
    }

    @Override
    public int getPeriod() {
        return 24*60;
    }

    @Override
    public JSONObject toJSON() {

        JSONObject json = new JSONObject();

        json.put("id", id);
        json.put("tag", TAG);
        json.put("date_debut", startDate);
        json.put("location", location);
        json.put("weather_type", weatherType);
        json.put("temperature", temperature);
        json.put("temperatureSelection", temperatureSelection);
        json.put("menuNotif", menuNotif);
        json.put("telegramNotif", telegramNotif);

        return json;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int getId() {
        return id;
    }

    @Override
    public String execute() {

        StringBuilder message = new StringBuilder();
        ServiceMeteo service = new ServiceMeteo();
        String temps = service.getMain(location);
        boolean sendTelegram = false;

        if (temps == null || temps.length() == 0) {
            return "An error has occured, maybe the location hasn't been written correctly, please try by adding another rule";
        }
        // Resultat de l'execution de la règle --> donner la meteo

        message.append("Voici la météo pour la ville de " + location + "\n\n");
        switch (temps) {
            case ("Clear") :
                message.append("Le temps est ensoleillé \n");
                break;
            case ("Rain") :
                message.append("Le temps est pluvieux \n");
                break;
            case("Clouds"):
                message.append("Le temps est couvert \n");
                break;
            case("Snow"):
                message.append("Il y a des chutes de neiges \n");
                break;
        }

        int temp = service.getTemperature(location);
        int tmp = Integer.parseInt(temperature);

        message.append("La temperature est de " + Integer.toString(temp) + " degrés Celsius. \n");

        // Si l'utilisateur a defini une regle concernant la temperature
        if (!temperatureSelection.equals("null")) {
            if (temperatureSelection.equals("<")) {
                if (temp <= tmp) {
                    sendTelegram = true;
                }
            } else {
                if (temp >= tmp) {
                    sendTelegram = true;
                }
            }
        }

        // Si l'utilisateur a defini une regle concernant le temps
        if (!weatherType.equals("null")) {
            if(weatherType.equals("Ensoleillé")) {
                if (service.isSunny(location)) {
                    sendTelegram = true;
                }
            } else if (weatherType.equals("Pluvieux")) {
                if (service.isRainy(location)) {
                    sendTelegram = true;
                }
            } else if (weatherType.equals("Neigeux")) {
                if (service.isSnowy(location)) {
                    sendTelegram = true;
                }
            } else if (weatherType.equals("Nuageux")) {
                if (service.isCloudy(location)) {
                    sendTelegram = true;
                }
            }
        }
        // Si l'utilisateur veut des notifications Telegram et que les conditions sont réunis on les envoies
        if (telegramNotif && sendTelegram) {
            // envoye notif to Telegram si les conditions du dessus sont présentes

            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());
            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId,telegram.encodeMessageForURL(message.toString()));

        }
        return message.toString();
    }
}
