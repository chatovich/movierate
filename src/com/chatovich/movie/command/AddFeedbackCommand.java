package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.service.FeedbackService;
import com.chatovich.movie.service.MovieService;
import com.chatovich.movie.service.UserService;
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
        MovieService movieService = new MovieService();
        Movie movie;
        try {
            feedbackService.createFeedback(user, id_movie, text, mark);
            movie = movieService.findMovieById(id_movie);
            request.setAttribute(Parameters.MOVIE, movie);
            request.setAttribute(Parameters.FEEDBACK_ADDED, true);
            session.setAttribute(Parameters.SIGNED_USER, userService.updateUserFeedbacks(user));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MOVIE_PAGE;
    }
}
