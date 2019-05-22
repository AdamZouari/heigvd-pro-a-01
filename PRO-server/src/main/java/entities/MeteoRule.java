package entities;

public class MeteoRule extends Rule {
    private static final String TAG = "METEO";
    private String time, location, weatherType, temperature, temperatureSelection, noteText;
    /**
     *
     * @param id
     **/
    public MeteoRule(int id,String username, String startDate, boolean telegramNotif, boolean menuNotif, String time, String location,
                     String weatherType, String temperature, String temperatureSelection, String noteText)
    {
        super(id,username, TAG, startDate, menuNotif,telegramNotif);
        this.time = time;
        this.location = location;
        this.weatherType = weatherType;
        this.temperature = temperature;
        this.temperatureSelection = temperatureSelection;
        this.noteText = noteText;
    }


    @Override
    public String execute() {
        return "";
    }
}
