package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.FeedbackService;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * command that picks the data entered by user to add a new feedback to a movie
 */
public class AddFeedbackCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(AddFeedbackCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(Parameters.FEEDBACK);
        long id_movie = Long.parseLong(request.getParameter(Parameters.ID_MOVIE));
        int mark = Integer.parseInt(request.getParameter(Parameters.MOVIE_MARK));
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(Parameters.SIGNED_USER);
        FeedbackService feedbackService = new FeedbackService();
        UserService userService = new UserService();
        try {
            feedbackService.createFeedback(user, id_movie, text, mark);
            session.setAttribute(Parameters.SIGNED_USER, userService.updateUserFeedbacks(user));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MAIN_PAGE;
    }
}
