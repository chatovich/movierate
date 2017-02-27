package com.chatovich.movie.command;

import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.UserServiceImpl;
import com.chatovich.movie.util.QueryUtil;
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
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();
        try {
            User user = userServiceImpl.getUser(login);
            session.setAttribute(Parameters.ANOTHER_USER, user);
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_PAGE;
    }
}
