package utils;

public class CheckForm {

   /**
    * Check if all the strings are filled
    *
    * @param strings    Strings to check
    * @return  True if all the strings contain a value, false otherwise
    */
   public static boolean isAllFilled(String... strings) {
      for(String str : strings) {
         if(str.isEmpty())
            return false;
      }

      return true;
   }

   /**
    * Check if the string matches to the regular expression
    *
    * @param str     String to check
    * @param regex   Regex used to check the string
    * @return  True if the string matches to the regex, false otherwise
    */
   public static boolean isValid(String str, String regex) {
      return str.matches(regex);
   }
}
