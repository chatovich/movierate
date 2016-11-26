package com.movierate.movie.util;

import com.movierate.movie.constant.Parameters;
import com.movierate.movie.constant.PatternValues;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yultos_ on 26.11.2016
 */
public class Validation {

    final static Logger LOGGER = LogManager.getLogger(Validation.class);

    public static List<String> checkRegistFormByPattern (Map<String, String[]> parameters){

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


    private static boolean matchPattern (String pattern, String value){
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(value);
        return mat.matches();
    }
}
