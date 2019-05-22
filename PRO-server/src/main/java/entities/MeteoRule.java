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
        json.put("telegram_notif", telegramNotif);

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

        JSONObject result = new JSONObject();
        ServiceMeteo service = new ServiceMeteo();
        String temps = service.getMain(location);
        boolean sendTelegram = false;

        // Resultat de l'execution de la règle --> donner la meteo
        switch (temps) {
            case ("Clear") :
                result.put("meteo","Ensoleillé");
            case ("Rain") :
                result.put("meteo","Pluvieuse");
            case("Cloud"):
                result.put("meteo", "Nuageuse");
            case("Snow"):
                result.put("meteo", "Chutes de neige");
        }

        int temp = service.getTemperature(location);
        int tmp = Integer.parseInt(temperature);

        result.put("temperature", temp);

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
        if (telegramNotif&& sendTelegram) {
            // envoye notif to Telegram si les conditions du dessus sont présentes

            // TODO HELP ANTOINE
            String telegramId = DatabaseController.getController().getTelegramIdByUsername(getUsername());
            TelegramNotification telegram = new TelegramNotification();
            telegram.sendRuleResult(telegramId, Integer.toString(new ServiceMeteo().getTemperature("Lausanne")));

        }
        if (menuNotif) {
            return result.toString();

        } else {
            return "";
        }
    }
}
