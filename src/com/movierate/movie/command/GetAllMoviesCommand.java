package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * command that gets all movies from the db
 */
public class GetAllMoviesCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {

        MovieService movieService = new MovieService();
        List<Movie> movies;
        try {
            movies = movieService.getAllMovies();
            request.setAttribute("chooseMovie", true);
            request.setAttribute("movies", movies);

        } catch (DAOFailedException e) {
            return PagePath.ERROR_PAGE;
        }

//        return PagePath.ADMIN_MAIN_PAGE;
        return PagePath.ADMIN_PAGE;
    }
}
