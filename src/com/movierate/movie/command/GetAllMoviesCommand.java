package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;
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
            request.setAttribute(Parameters.CHOOSE_MOVIE, true);
            request.setAttribute(Parameters.MOVIES, movies);

        } catch (ServiceException e) {
            return PagePath.ERROR_PAGE;
        }

        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
