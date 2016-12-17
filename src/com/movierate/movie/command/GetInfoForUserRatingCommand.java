package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.UserService;
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
        if (signedUser==null){
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
        }
        else if (Long.parseLong(id)!=signedUser.getId()){
            request.setAttribute(Parameters.SHOW_ANOTHER_USER, true);
        }
        UserService userService = new UserService();
        try {
            Double rating = userService.calcUserRating(id);
            request.setAttribute(Parameters.USER_RATING, rating);
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_ACTIVITY_PAGE;
    }
}
