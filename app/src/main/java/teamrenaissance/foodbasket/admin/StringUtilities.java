package teamrenaissance.foodbasket.admin;

/**
 * Provides utilities for checking validity of Strings input by users
 *
 */
public class StringUtilities {

    public static boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }

    public static boolean isAlphaNumericPunctuation(String s){
        String pattern= "^[a-zA-Z0-9 .!,]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }

    public static boolean isEmail(String s){
        String pattern = "[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }
}
