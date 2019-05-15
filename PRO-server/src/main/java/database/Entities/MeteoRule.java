package database.Entities;

public class MeteoRule extends Rule {
    private String tag;
    /**
     *
     * @param id
     * @param tag
     * @param startDate
     * @param periodicity
     * @param content
     **/
    public MeteoRule(String id, String tag, String startDate, String periodicity, String content) {
        super(id, startDate, periodicity, content);
        this.tag= "meteo";
    }

    @Override
    void formatRuleToSendServer() {

    }
}
