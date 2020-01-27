package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Andoni Yeray
 */
public class Validator {
    
    /**
     * Method that check if the email is well written
     * @param email parameter that is recibed from de interface
     * @return true if the email format is usefull.
     */
    public static boolean emailChecker(String email){
        boolean auxEmail = true;
        
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
 
        if (!mather.find()) {
            auxEmail = false;
        }
        return auxEmail;
    }
    /**
     * Method that check if the password complete the requirement
     * @param password parameter that is recibed from de interface
     * @return true if the password complete the requirements
     */
    
     public static boolean passwordChecker(String password){
        boolean auxPwd = true;
        //TODO
        Pattern pattern = Pattern
                .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$");

        Matcher mather = pattern.matcher(password);
 
        if (!mather.find()) {
            auxPwd = false;
        }
        return auxPwd;
    }
     
}