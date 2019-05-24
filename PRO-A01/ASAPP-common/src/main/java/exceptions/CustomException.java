package exceptions;

import protocol.ExceptionCodes;

/**
 * This class represent an Exception of the ASSAP Application
 */
public class CustomException extends Exception {

    private int exceptionNumber = -1;

    /**
     * Minimalistic constructor
     */
    public CustomException() {
        super();
    }

    /**
     * Constructor taking an exceptionNumber
     *
     * @param exceptionNumber the number of the error to throw
     */
    public CustomException(int exceptionNumber) {
        super();
        setExceptionNumber(exceptionNumber);
    }

    /**
     * Constructor
     *
     * @param m the message of the Exception
     */
    public CustomException(String m) {
        super(m);
    }

    public int getExceptionNumber() {
        return exceptionNumber;
    }

    public void setExceptionNumber(int exceptionNumber) {
        this.exceptionNumber = exceptionNumber;
    }

    /**
     * This method overrides the Exception.getMessage()
     * If an exceptionNumber is defined, will fetch the corresponding message
     *
     * @return
     */
    @Override
    public String getMessage() {
        String message = null;
        if (exceptionNumber >= 0) {
            message = ExceptionCodes.values()[exceptionNumber].getMessage();
        } else {
            message = super.getMessage();
        }
        return message;
    }
}
