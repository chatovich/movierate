package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.util.QueryUtil;
import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * command that picks the data entered by admin to add a new movie on the website
 */
public class AddMovieCommand extends UploadPhoto implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(AddMovieCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute(Parameters.EMPTY_FIELDS, true);
            return PagePath.USER_PAGE;
        }
        MovieService movieService = new MovieService();

        try {
            if (movieService.movieExists(request.getParameter(Parameters.PARAM_TITLE))){
                request.setAttribute(Parameters.MOVIE_EXISTS, true);
                return PagePath.USER_PAGE;
            }
            //get uploaded photo if there was one
            String path = uploadFile(request, Parameters.POSTER_FILE_PATH, Parameters.POSTER);
            movieService.createMovie(parameters,path);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.setAttribute(Parameters.MOVIE_ADDED, true);
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
