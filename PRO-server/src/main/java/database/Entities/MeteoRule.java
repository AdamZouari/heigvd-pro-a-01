package database.Entities;

public class MeteoRule extends Rule {
    private String tag;
    private String time;
    /**
     *
     * @param id
     * @param tag
     * @param startDate
     * @param periodicity
     * @param content
     **/
    public MeteoRule(String id, String tag, String startDate, String periodicity, String content) {

    }

    @Override
    void formatRuleToSendServer() {

    }
}
