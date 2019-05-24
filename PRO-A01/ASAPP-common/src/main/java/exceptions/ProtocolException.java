package exceptions;

import java.lang.Exception;

public class ProtocolException extends Exception {

    /**
     * Minimalistic constructor
     */
    public ProtocolException() {
        super();
    }

    /**
     * Constructor
     *
     * @param m the message of the Exception
     */
    public ProtocolException(String m) {
        super(m);
    }

}
