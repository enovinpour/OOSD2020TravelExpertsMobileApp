package org.ehsan.travelexpertsoosd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidEmailNoAlert(String s) {
        boolean isValid = true;
        String email = s;
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            isValid = false;
        }

        return isValid;
    }

    public static boolean isValidPassword(String p) {
        boolean isValid = true;
        String password = p;
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            isValid = false;
        }

        return isValid;

        //EXPLANATION PASSWORD REGEX//
//       ^                 # start-of-string
//       (?=.*[0-9])       # a digit must occur at least once
//       (?=.*[a-z])       # a lower case letter must occur at least once
//       (?=.*[A-Z])       # an upper case letter must occur at least once
//       (?=.*[@#$%^&+=])  # a special character must occur at least once
//       (?=\S+$)          # no whitespace allowed in the entire string
//       .{8,}             # anything, at least eight places though
//       $                 # end-of-string
    }
}
