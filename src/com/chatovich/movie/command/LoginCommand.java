package com.chatovich.movie.command;

import com.chatovich.movie.exception.HashPasswordFailedException;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.service.IMovieService;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.FeedbackServiceImpl;
import com.chatovich.movie.service.impl.MovieServiceImpl;
import com.chatovich.movie.service.impl.UserServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * command that picks data entered by a user who wants to log in
 */
public class LoginCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute(Parameters.EMPTY_FIELDS, true);
            return PagePath.LOGIN_PAGE;
        }
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();
        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        try {
            User user = userServiceImpl.getUser(parameters.get(Parameters.LOGIN)[0]);
            if (Validation.loginInfoValid(user, parameters)){
                HttpSession session = request.getSession(true);
                session.setAttribute(Parameters.USER_SIGNED_IN, true);
                session.setAttribute(Parameters.SIGNED_USER, user);
                List<Feedback> latestFeedbacks = feedbackServiceImpl.findLatestFeedbacks();
                request.setAttribute(Parameters.LATEST_FEEDBACKS, latestFeedbacks);
                List<Movie> topMovies = movieServiceImpl.findTopMovies();
                request.setAttribute(Parameters.TOP_MOVIES, topMovies);
            } else {
                request.setAttribute(Parameters.LOGIN_FAILED, true);
                return PagePath.LOGIN_PAGE;
            }
        } catch (ServiceException|HashPasswordFailedException e) {
            LOGGER.log(Level.ERROR, e);
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MAIN_PAGE;
    }
}
