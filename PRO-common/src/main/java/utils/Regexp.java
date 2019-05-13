package utils;

public class Regexp {
   public final static String PSEUDO_TELEGRAM = "([A-Za-z0-9]|_){5,32}";

   public final static String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
}
