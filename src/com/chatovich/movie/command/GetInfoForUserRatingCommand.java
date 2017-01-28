package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.UserServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * gets movies which were commented by signed user
 */
public class GetInfoForUserRatingCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(GetInfoForUserRatingCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter(Parameters.ID_USER);
        User signedUser = (User)request.getSession().getAttribute(Parameters.SIGNED_USER);
        if (signedUser==null||Long.parseLong(id)!=signedUser.getId()){
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
        }
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();
        try {
            Double rating = userServiceImpl.defineUserRating(id);
            request.setAttribute(Parameters.USER_RATING, rating);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_ACTIVITY_PAGE;
    }
}
