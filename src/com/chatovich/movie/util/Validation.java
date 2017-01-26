package com.chatovich.movie.util;

import com.chatovich.movie.constant.PatternValues;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.exception.HashPasswordFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validation methods
 */
public class Validation {

    /**
     * checks whether all of the obligatory fields were filled
     * @param parameters parameters that had to be filled
     * @return list of the parameters that weren't filled
     */
    public static List<String> checkEmptyFields (Map<String, String[]> parameters){
        List<String> wrongParameters = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (entry.getValue()[0].isEmpty()){
                wrongParameters.add(entry.getKey());
            }
        }
        return wrongParameters;
    }

    /**
     * checks whether fields in the registration form were filled according to patterns
     * @param parameters parameters that had to be filled
     * @return list of the parameters that weren't filled correctly
     */
    public static List<String> checkRegistrFormByPattern(Map<String, String[]> parameters){

        List<String> wrongParameters = new ArrayList<>();
        boolean isMatch;
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            switch (entry.getKey()){
                case "username": isMatch = matchPattern(PatternValues.LOGIN_PATTERN, entry.getValue()[0]);
                    if (!isMatch){
                        wrongParameters.add(entry.getKey());
                    }
                    break;
                case "password": isMatch = matchPattern(PatternValues.LOGIN_PATTERN, entry.getValue()[0]);
                    if (!isMatch){
                        wrongParameters.add(entry.getKey());
                    }
                    break;
                case "confirm_password": isMatch = matchPattern(PatternValues.LOGIN_PATTERN, entry.getValue()[0]);
                    if (!isMatch){
                        wrongParameters.add(entry.getKey());
                    }
                    break;
                case "email": isMatch = matchPattern(PatternValues.EMAIL_PATTERN, entry.getValue()[0]);
                    if (!isMatch){
                        wrongParameters.add(entry.getKey());
                    }
                    break;
                default: break;
            }
        }
        return wrongParameters;
    }

    /**
     * checks whether passwords in both fields are equal
     * @param parameters parameters that had to be filled with password and its confirmation
     * @return true if passwords match, otherwise - false
     */
    public static boolean checkPasswordConfirm (Map<String, String[]> parameters){
        String password1 = "";
        String password2 = null;
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (entry.getKey().equals(Parameters.PASSWORD)){
                password1 = entry.getValue()[0];
            }
            if (entry.getKey().equals(Parameters.PASSWORD_CONFIRM)){
                password2 = entry.getValue()[0];
            }
        }
        return password1.equals(password2);
    }

    /**
     * checks whether user entered valid login and password during authorization
     * @param user user who wants to log in
     * @param parameters parameters that were filled by user
     * @return true if user entered valid info, otherwise - false
     * @throws HashPasswordFailedException if there was a problem during hash password
     */
    public static boolean loginInfoValid (User user, Map<String, String[]> parameters) throws HashPasswordFailedException {
        String login = parameters.get("login")[0];
        if (login.equals(user.getLogin())){
            String hashedPassword = PasswordHash.getHashPassword(parameters.get("password")[0]);
            if (hashedPassword.equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

    /**
     * checks whether value matches pattern
     * @param pattern pattern
     * @param value String value to be checked
     * @return true if value matches, otherwise - false
     */
    private static boolean matchPattern (String pattern, String value){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(value);
        return mat.matches();
    }
}
