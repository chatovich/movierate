package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.util.Validation;
import com.chatovich.movie.service.MovieService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * command that picks the data entered by admin to edit movie info
 */
public class UpdateMovieCommand extends UploadPhoto implements ICommand{

    private static final Logger LOGGER = LogManager.getLogger(UpdateMovieCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute(Parameters.EMPTY_FIELDS, true);
            return PagePath.USER_PAGE;
        }
        MovieService movieService = new MovieService();
        //get uploaded photo if there was one
        String path = uploadFile(request, Parameters.POSTER_FILE_PATH, Parameters.POSTER);
        try {
            movieService.createMovie(parameters,path);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e);
            return PagePath.ERROR_PAGE;
        }
        request.setAttribute(Parameters.MOVIE_UPDATED, true);
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
