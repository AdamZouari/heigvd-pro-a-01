package entities;

import service.ServiceMeteo;

public class MeteoRule extends Rule {
    private static final String TAG = "METEO";
    private String location, weatherType, temperature, temperatureSelection, noteText;
    /**
     *
     * @param id
     **/
    public MeteoRule(int id, String startDate, boolean telegramNotif, boolean menuNotif, String location,
                     String weatherType, String temperature, String temperatureSelection, String noteText)
    {
        super(id, TAG, startDate, menuNotif,telegramNotif);
        this.location = location;
        this.weatherType = weatherType;
        this.temperature = temperature;
        this.temperatureSelection = temperatureSelection;
        this.noteText = noteText;
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
