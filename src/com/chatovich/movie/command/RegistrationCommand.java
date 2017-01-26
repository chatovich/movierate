package com.chatovich.movie.command;

import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.service.FeedbackService;
import com.chatovich.movie.service.MovieService;
import com.chatovich.movie.service.UserService;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * command that picks data for registration entered by a new user
 */
public class RegistrationCommand extends UploadPhoto implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        List<String> wrongParameters = Validation.checkRegistrFormByPattern(parameters);

        if (!wrongParameters.isEmpty()){
            request.setAttribute(Parameters.REGISTR_FAILED, true);
            for (int i = 0; i < wrongParameters.size(); i++) {
                request.setAttribute(wrongParameters.get(i)+"Wrong", wrongParameters.get(i));
            }
            return PagePath.REGISTR_PAGE;
        }
        if (!Validation.checkPasswordConfirm(parameters)){
            request.setAttribute(Parameters.PASSWORDS_NO_MATCH, true);
            return PagePath.REGISTR_PAGE;
        }

        UserService userService = new UserService();
        FeedbackService feedbackService = new FeedbackService();
        MovieService movieService = new MovieService();
        //get uploaded photo if there was one
        String path = uploadFile(request, Parameters.PHOTO_FILE_PATH, Parameters.PHOTO);
        try {
            if (!userService.loginAvailable(Parameters.USERNAME)){
                request.setAttribute(Parameters.LOGIN_EXISTS, true);
                return PagePath.REGISTR_PAGE;
            }
            userService.createUser(parameters,path);
            request.setAttribute(Parameters.REGISTR_FAILED, false);
            List<Feedback> latestFeedbacks = feedbackService.findLatestFeedbacks();
            request.setAttribute(Parameters.LATEST_FEEDBACKS, latestFeedbacks);
            List<Movie> topMovies = movieService.findTopMovies();
            request.setAttribute(Parameters.TOP_MOVIES, topMovies);
            request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.MAIN_PAGE;
    }
}
