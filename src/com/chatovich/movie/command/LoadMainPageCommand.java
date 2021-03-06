package com.chatovich.movie.command;

import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Feedback;
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
 * gets all necessary data to load main page
 */
public class LoadMainPageCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(LoadMainPageCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        try {
            List<Feedback> latestFeedbacks = feedbackServiceImpl.findLatestFeedbacks();
            request.setAttribute(Parameters.LATEST_FEEDBACKS, latestFeedbacks);
            List<Movie> topMovies = movieServiceImpl.findTopMovies();
            request.setAttribute(Parameters.TOP_MOVIES, topMovies);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.MAIN_PAGE;
    }
}
