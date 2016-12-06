package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.dao.impl.UserDAOImpl;
import com.movierate.movie.entity.User;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Yultos_ on 26.11.2016
 */
public class RegistrationCommand extends UploadPhoto implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);
    private static final String ATTR_REGISTR_FAILED = "registrFailed";
    private static final String ATTR_PASSWORDS_NO_MATCH = "passwordsNoMatch";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PHOTO = "photo";
    private static final String ATTR_LOGIN_EXISTS = "loginExists";
    private static final String FILE_PATH = "/img/photo";

    @Override
    public String execute(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        List<String> wrongParameters = Validation.checkRegistrFormByPattern(parameters);

        if (!wrongParameters.isEmpty()){
            request.setAttribute(ATTR_REGISTR_FAILED, true);
            for (int i = 0; i < wrongParameters.size(); i++) {
                request.setAttribute(wrongParameters.get(i)+"Wrong", wrongParameters.get(i));
            }
            return PagePath.REGISTR_PAGE;
        }
        if (!Validation.checkPasswordConfirm(parameters)){
            request.setAttribute(ATTR_PASSWORDS_NO_MATCH, true);
            return PagePath.REGISTR_PAGE;
        }

        UserService userService = new UserService();
        if (!userService.loginAvailable(PARAM_USERNAME)){
            request.setAttribute(ATTR_LOGIN_EXISTS, true);
            return PagePath.REGISTR_PAGE;
        }

        //get uploaded photo if there was one
        String path = uploadFile(request, FILE_PATH, PARAM_PHOTO);
        boolean isCreated = userService.createUser(parameters,path);
//        request.setAttribute("registrFailed", false);
//        return "jsp/main/main.jsp";
        if (isCreated){
            request.setAttribute(ATTR_REGISTR_FAILED, false);
            return PagePath.MAIN_PAGE;
        } else {
            return PagePath.ERROR_PAGE;
        }

    }
}
