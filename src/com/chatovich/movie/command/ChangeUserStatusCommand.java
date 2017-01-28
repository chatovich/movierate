package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.UserServiceImpl;
import com.chatovich.movie.util.QueryUtil;
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
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();

        try {
            userServiceImpl.changeUserStatus(login,Boolean.valueOf(toBan));
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
            User user = userServiceImpl.getUser(login);
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
