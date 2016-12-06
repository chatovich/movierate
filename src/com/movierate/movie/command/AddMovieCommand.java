package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * command that picks the data entered by admin to add a new movie on the website
 */
public class AddMovieCommand extends UploadPhoto implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(AddMovieCommand.class);
    private static final String FILE_PATH = "/img/poster";
    private static final String PARAM_POSTER = "poster";
    private static final String PARAM_TITLE = "title";

    @Override
    public String execute(HttpServletRequest request) {

        MovieService movieService = new MovieService();
        if (movieService.movieExists(request.getParameter(PARAM_TITLE))){
            request.setAttribute("movieExists", true);
            request.setAttribute("add_movie", true);
            return PagePath.ADMIN_MAIN_PAGE;
        }
        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkAddMovieForm(parameters).isEmpty()){
            request.setAttribute("emptyField", true);
            request.setAttribute("add_movie", true);
            return PagePath.ADMIN_MAIN_PAGE;
        }
        //get uploaded photo if there was one
        String path = uploadFile(request, FILE_PATH, PARAM_POSTER);
        movieService.createMovie(parameters,path);
        return PagePath.MAIN_PAGE;
    }
}
