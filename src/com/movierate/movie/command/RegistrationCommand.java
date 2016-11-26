package com.movierate.movie.command;

import com.movierate.movie.util.Validation;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yultos_ on 26.11.2016
 */
public class RegistrationCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        List<String> wrongParameters = Validation.checkRegistFormByPattern(parameters);

        if (!wrongParameters.isEmpty()){
            request.setAttribute("registrFailed", true);
            for (int i = 0; i < wrongParameters.size(); i++) {
                request.setAttribute(wrongParameters.get(i)+"Wrong", wrongParameters.get(i));
            }
            return "jsp/login/regCop.jsp";
        }
        if (!Validation.checkPasswordConfirm(parameters)){
            request.setAttribute("passwordsNoMatch", true);
            return "jsp/login/regCop.jsp";
        }
        request.setAttribute("registrFailed", false);
        return "jsp/main/main.jsp";

    }
}
