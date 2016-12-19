package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.dao.impl.UserDAOImpl;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.QueryUtil;
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
 * command that picks data for registration entered by a new user
 */
public class RegistrationCommand extends UploadPhoto implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);
    private static final String ATTR_REGISTR_FAILED = "registrFailed";
    private static final String ATTR_PASSWORDS_NO_MATCH = "passwordsNoMatch";
    private static final String PARAM_USERNAME = "username";

    private static final String ATTR_LOGIN_EXISTS = "loginExists";


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
        try {
            if (!userService.loginAvailable(PARAM_USERNAME)){
                request.setAttribute(ATTR_LOGIN_EXISTS, true);
                return PagePath.REGISTR_PAGE;
            }
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, e);
        }

        //get uploaded photo if there was one
        String path = uploadFile(request, Parameters.PHOTO_FILE_PATH, Parameters.PHOTO);
        boolean isCreated = userService.createUser(parameters,path);
//        request.setAttribute("registrFailed", false);
//        return "jsp/main/main.jsp";
        if (isCreated){
            request.setAttribute(ATTR_REGISTR_FAILED, false);
            request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
            return PagePath.MAIN_PAGE;
        } else {
            return PagePath.ERROR_PAGE;
        }

    }
}
