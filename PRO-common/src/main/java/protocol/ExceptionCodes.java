package protocol;

public enum ExceptionCodes {

    A_USER_ALREADY_EXISTS_WITH_THIS_PSEUDO("A user already exists with this pseudo."),
    A_USER_ALREADY_EXISTS_WITH_THIS_TELEGRAM("A user already exists with this Telegram account."),
    REGISTRATION_FAILED("Registration failed."),
    LOGIN_FAILED("Wrong username or password"),
    ALL_FIELDS_ARE_NOT_FILLED("All fields are mandatory !"),
    DEPARTURE_IS_NOT_A_CITY("Departure city hasn't a valid name !"),
    ARRIVAL_IS_NOT_A_CITY("Arrival city hasn't a valid name !"),
    DEPARTURE_IS_NOT_IN_TIME_FORMAT("Departure time isn't in HH:MM format !"),
    REQUEST_HOUR_IS_NOT_IN_TIME_FORMAT("Request hour isn't in HH:MM format !"),
    PASSWORDS_DID_NOT_MATCH("Passwords didn't match !"),
    PASSWORD_INVALID("Password should have 8 characters, lowercase, uppercase, number and special chars !"),
    INVALID_PSEUDO_TELEGRAM("Pseudo Telegram is not valid !"),
    REQUEST_APPEARS_NOWHERE("The request has to appear in the menu or in a Telegram notification !"),
    LOCATION_AND_TIME_MISSING("The location and the hour of the request are mandatory !"),
    WEATHER_TYPE_OR_TEMPERATURE_CONDITION("The weather type or a condition for the temperature should be selected !"),
    TEMPERATURE_MISSING("The temperature is missing !"),
    LOCATION_IS_NOT_A_CITY("Location city hasn't a valid name !"),
    NOT_A_NUMBER("The temperature isn't a number !"),
    NAME_MISSING("The name is missing !"),
    SOME_PASS_ARE_MISSING("All the password fields are mandatory !"),
    USER_DIDNT_ADD_TELEGRAM_BOT("The bot telegram haven't been added !"),
    UPDATE_OF_USER_FAILED("Update of user failed."),
    UPDATE_OF_RULE_FAILED("Update of user failed."),
    FAIL_TO_FETCH_USER_FROM_DB("Fetch user failed."),
    FAIL_TO_FETCH_RULES_FROM_DB("Fetch rules failed.");



    String message;

    ExceptionCodes(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }
}
