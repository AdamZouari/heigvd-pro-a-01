package database.entities;

public class TwitterRule extends Rule {

    private String tag;

    /**
     * @param id
     * @param tag
     * @param startDate
     * @param periodicity
     * @param content
     **/
    public TwitterRule(String id, String tag, String startDate, String periodicity, String content) {
        super(id, startDate, periodicity, content);
        this.tag= "twitter";

    }

    @Override
    void formatRuleToSendServer() {

    }
}
