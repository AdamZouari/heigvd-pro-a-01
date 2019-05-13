package protocol;

public enum ExceptionCodes {

    A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO("Un utilisateur avec ce pseudo existe déjà."),
    A_USER_ALREADY_EXISTS_WITH_THIS_TELEGRAM("Un utilisateur avec ce compte Telegram existe déjà."),
    REGISTRATION_FAILED("L'inscription a échoué"),
    LOGIN_FAILED("Wrong username or password"),
    ALL_FIELDS_ARE_NOT_FILLED("All fields are mandatory !"),
    DEPARTURE_IS_NOT_A_CITY("Departure city hasn't a valid name"),
    ARRIVAL_IS_NOT_A_CITY("Arrival city hasn't a valid name"),
    DEPARTURE_IS_NOT_IN_TIME_FORMAT("Departure time isn't in HH:MM format"),
    REQUEST_HOUR_IS_NOT_IN_TIME_FORMAT("Request hour isn't in HH:MM format"),
    PASSWORDS_DID_NOT_MATCH("Passwords didn't match !"),
    PASSWORD_INVALID("Password should have 8 characters, lowercase, uppercase, number and special chars !"),
    INVALID_PSEUDO_TELEGRAM("Pseudo Telegram is not valid !"),
    REQUEST_APPEARS_NOWHERE("The request has to appear in the menu or in a Telegram notification !");

    String message;

    ExceptionCodes(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }
}
