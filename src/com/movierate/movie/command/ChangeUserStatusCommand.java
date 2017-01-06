package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * bans user and restricts access to several functions on the web site
 */
public class ChangeUserStatusCommand implements ICommand{

    private static final Logger LOGGER = LogManager.getLogger(ChangeUserStatusCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(Parameters.ID_USER);
        String toBan = request.getParameter(Parameters.BAN);
        UserService userService = new UserService();

        try {
            userService.changeUserStatus(login,Boolean.valueOf(toBan));
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
            User user = userService.getUser(login);
            request.getSession().setAttribute(Parameters.ANOTHER_USER, user);
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
