package protocol;

public enum ExceptionCodes {

    A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO("Un utilisateur avec ce pseudo existe déjà.");

    String message;

    ExceptionCodes(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }
}
