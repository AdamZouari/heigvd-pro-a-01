package entities;

public class RtsRule extends Rule {

    private static final String TAG = "RTS";
    private String channel,requestTime;

    /**
     * @param id
     * @param startDate
     **/
    public RtsRule(int id,String username, String startDate,String channel,String requestTime, boolean menuNotif,boolean telegramCheckBox) {
        super(id,username, TAG,startDate,menuNotif,telegramCheckBox);
        this.channel=channel;
        this.requestTime=requestTime;
    }


    @Override
    public String execute() {
        // TO DO
        return "";
    }
}
