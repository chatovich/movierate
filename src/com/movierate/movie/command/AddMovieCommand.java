package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.exception.DAOFailedException;
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

        MovieService movieService = new MovieService();
        if (movieService.movieExists(request.getParameter(Parameters.PARAM_TITLE))){
            request.setAttribute("movieExists", true);
            return PagePath.USER_PAGE;
        }
        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute("emptyField", true);
            return PagePath.USER_PAGE;
        }
        //get uploaded photo if there was one
        String path = uploadFile(request, Parameters.POSTER_FILE_PATH, Parameters.POSTER);
        try {
            movieService.createMovie(parameters,path);
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, "Impossible to add a movie"+e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.setAttribute("movieAdded", true);
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
