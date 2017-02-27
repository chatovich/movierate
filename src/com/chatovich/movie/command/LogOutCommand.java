package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.service.IMovieService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.FeedbackServiceImpl;
import com.chatovich.movie.service.impl.MovieServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * invalidates the session when user wants to log out
 */
public class LogOutCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().invalidate();
        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        try {
            List<Feedback> latestFeedbacks = feedbackServiceImpl.findLatestFeedbacks();
            request.setAttribute(Parameters.LATEST_FEEDBACKS, latestFeedbacks);
            List<Movie> topMovies = movieServiceImpl.findTopMovies();
            request.setAttribute(Parameters.TOP_MOVIES, topMovies);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e);
            return PagePath.ERROR_PAGE;
        }
        return PagePath.MAIN_PAGE;
    }
}
