package entities;

import org.json.JSONObject;

public class RtsRule extends Rule {

    private static final String TAG = "RTS";
    private String channel,requestTime;

    /**
     * @param id
     * @param startDate
     **/
    public RtsRule(int id, String startDate,String channel,String requestTime, boolean menuNotif,boolean telegramCheckBox) {
        super(id, TAG,startDate,menuNotif,telegramCheckBox);
        this.channel=channel;
        this.requestTime=requestTime;
    }


    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public String execute() {
        // TO DO
        return "";
    }
}
