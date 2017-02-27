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
import java.util.List;

/**
 * gets the list of all users from the db
 */
public class GetAllUsersCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetAllUsersCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();
        try {
            List<User> users = userServiceImpl.getAllUsers();
            request.setAttribute(Parameters.USERS, users);
            request.setAttribute(Parameters.CHOOSE_USER, true);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_PAGE;
    }
}
