package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.util.Validation;
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
            request.setAttribute("emptyField", true);
            return PagePath.USER_PAGE;
        }
        MovieService movieService = new MovieService();
        //get uploaded photo if there was one
        String path = uploadFile(request, Parameters.POSTER_FILE_PATH, Parameters.POSTER);
        try {
            movieService.createMovie(parameters,path);
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, "Impossible to add a movie"+e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.setAttribute("movieUpdated", true);
        return PagePath.USER_PAGE;
    }
}
