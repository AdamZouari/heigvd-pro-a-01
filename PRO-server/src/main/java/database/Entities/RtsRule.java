package database.Entities;

public class RtsRule extends Rule {

    private String tag;

    /**
     * @param id
     * @param tag
     * @param startDate
     * @param periodicity
     * @param content
     **/
    public RtsRule(String id, String tag, String startDate, String periodicity, String content) {
        super(id, startDate, periodicity, content);
        this.tag= "rts";

    }

    @Override
    void formatRuleToSendServer() {

    }
}
