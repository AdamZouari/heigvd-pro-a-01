package utils;

public class Regexp {
   public final static String PSEUDO_TELEGRAM = "([A-Za-z0-9]|_){5,32}";

   public final static String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

   public final static String CITY = "^[A-Z]([A-Za-z\\s]|-)*[a-z]$";

   public final static String TIME = "^((0|1)[0-9]|2[0-3]):[0-5][0-9]$";

   public final static String NUMBER = "^-?\\d+(\\.\\d)?";
}
