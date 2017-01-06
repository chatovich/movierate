package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * gets the page of another user (who is not signed in)
 */
public class GetAnotherUserPageCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(GetAnotherUserPageCommand.class);
    @Override
    public String execute(HttpServletRequest request) {

        String login = request.getParameter(Parameters.LOGIN);
        HttpSession session = request.getSession(true);
        UserService userService = new UserService();
        try {
            User user = userService.getUser(login);
            session.setAttribute(Parameters.ANOTHER_USER, user);
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
