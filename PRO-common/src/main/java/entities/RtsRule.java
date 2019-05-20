package entities;

public class RtsRule extends Rule {

    private static final String TAG = "RTS";

    /**
     * @param id
     * @param startDate
     **/
    public RtsRule(int id, String startDate,boolean menuCheckBox,boolean telegramCheckBox) {
        super(id, TAG,startDate,menuCheckBox,telegramCheckBox);
    }

    @Override
    void formatRuleToSendServer() {

    }
}
