package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.FeedbackService;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.QueryUtil;
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
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        UserService userService = new UserService();
        FeedbackService feedbackService = new FeedbackService();
        MovieService movieService = new MovieService();
        try {
            List<Feedback> latestFeedbacks = feedbackService.findLatestFeedbacks();
            request.setAttribute(Parameters.LATEST_FEEDBACKS, latestFeedbacks);
            List<Movie> topMovies = movieService.findTopMovies();
            request.setAttribute(Parameters.TOP_MOVIES, topMovies);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        userService.controlBan();
        return PagePath.MAIN_PAGE;
    }
}
