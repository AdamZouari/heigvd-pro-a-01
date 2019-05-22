package entities;

import org.json.JSONObject;
import service.ServiceMeteo;

public class MeteoRule extends Rule {

    private static final String TAG = "METEO";
    private String location, weatherType, temperature, temperatureSelection;
    /**
     *
     * @param id
     **/
    public MeteoRule(int id, String startDate, boolean telegramNotif, boolean menuNotif, String location,
                     String weatherType, String temperature, String temperatureSelection)
    {
        super(id, TAG, startDate, menuNotif,telegramNotif);
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
        json.put("tag", tag);
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

        String result = new String();

        ServiceMeteo service = new ServiceMeteo();

        int temp = service.getTemperature(location);
        int tmp = Integer.parseInt(temperature);

        // Si l'utilisateur a defini une regle concernant la temperature
        if (temperatureSelection != null) {
            if (temperatureSelection.equals("<")) {
                if (temp <= tmp) {
                    // Notify
                }
            } else {
                if (temp >= tmp) {
                    // Notify
                }
            }
        }

        // Si l'utilisateur a defini une regle concernant le temps
        if (weatherType != null) {
            if(weatherType.equals("sunny")) {
                if (service.isSunny(location)) {
                    // notify
                }
            } else if (weatherType.equals("rainy")) {
                if (service.isRainy(location)) {
                    // notify
                }
            } else if (weatherType.equals("snowy")) {
                if (service.isSnowy(location)) {
                    // notify
                }
            }
        }
        // Regardez si on envoie des notifs sur telegram
        if (telegramNotif) {
            // A voir
        }

        return result;

    }
}
