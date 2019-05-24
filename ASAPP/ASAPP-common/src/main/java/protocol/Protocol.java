package protocol;

public class Protocol {

    public final static int DEFAULT_PORT = 2222;
    //public static final String HOST = "ec2-35-181-66-105.eu-west-3.compute.amazonaws.com";
    public static final String HOST = "localhost";

    public final static char EOL = '\n';

    // LOG <username>:<password>
    public final static String CMD_LOG = "LOG";

    // REG <username>:<usernameTelegram>:<hashPassword>
    public final static String CMD_REG = "REG";

    public final static String CMD_GET_RES_RULES = "GET_RES_RULES";
    public final static String CMD_GET_RULES = "GET_RULES";
    public final static String CMD_GET_CFF = "GET_CFF";

    public static final String CMD_ADD_RULE = "ADD_RULE";
    public static final String CMD_DELETE_RULE = "DELETE_RULE";
    public static final String CMD_DELETE_USER_RULES = "DELETE_USER_RULES";

    public static final String CMD_GET_LANGUAGE = "GET_LANGUAGE";
    public static final String CMD_SET_LANGUAGE = "SET_LANGUAGE";

    public final static String RESPONSE_SUCCESS = "SUCCESS";
    public final static String RESPONSE_FAILURE = "FAILURE";
}
