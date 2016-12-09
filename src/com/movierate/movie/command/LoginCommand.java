package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.dao.impl.UserDAOImpl;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * command that picks data entered by a user who wants to log in
 */
public class LoginCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute(Parameters.ATTR_EMPTY_FIELDS, true);
            return PagePath.LOGIN_PAGE;
        }
        UserService userService = new UserService();
        try {
            User user = userService.getLoginInfo(parameters);
            if (Validation.loginInfoValid(user, parameters)){
                HttpSession session = request.getSession(true);
                session.setAttribute(Parameters.ATTR_USER_SIGNED_IN, true);
                session.setAttribute(Parameters.USER, user);
            } else {
                request.setAttribute(Parameters.ATTR_LOGIN_FAILED, true);
                return PagePath.LOGIN_PAGE;
            }
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, e);
        }
        return PagePath.MAIN_PAGE;
    }
}