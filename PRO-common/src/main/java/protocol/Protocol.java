package protocol;

public class Protocol {

    public final static int DEFAULT_PORT = 2222;

    public final static char EOL = '\n';

    public final static String CMD_WELCOME = "WELCOME";

    // LOG <username>:<password>
    public final static String CMD_LOG = "LOG";

    // REG <username>:<usernameTelegram>:<hashPassword>
    public final static String CMD_REG = "REG";

    public final static String CMD_GET_RES_RULES = "GET_RES_RULES";
    public final static String CMD_GET_RULES = "GET_RULES";

    public final static String CMD_CREATE_RULE = "CREATE_RULE";
    public final static String CMD_SEND_RULE = "SEND_RULE";

    public final static String CMD_GET_CFF = "GET_CFF";


    public final static String RESPONSE_SUCCESS = "SUCCESS";
    public final static String RESPONSE_FAILURE = "FAILURE";

    public static final String CMD_ADD_RULE = "ADD_RULE";
    public static final String CMD_DELETE_RULE = "DELETE_RULE";

}
