package protocol;

public enum ExceptionCodes {

    A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO("Un utilisateur avec ce pseudo existe déjà."),
    A_USER_ALREADY_EXISTS_WITH_THIS_TELEGRAM("Un utilisateur avec ce compte telegram existe déjà."),
    REGISTRATION_FAILED("L'inscription a échoué");

    String message;

    ExceptionCodes(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }
}
