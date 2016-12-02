package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that picks data needed for build a movie page
 */
public class GetMoviePage implements ICommand {

    private static final String PARAM_ID = "id";
    private static final String ATTR_MOVIE = "movie";

    @Override
    public String execute(HttpServletRequest request) {

        int id_movie = Integer.parseInt(request.getParameter(PARAM_ID));
        MovieService movieService = new MovieService();
        Movie movie = movieService.findMovieById(id_movie);
        request.setAttribute(ATTR_MOVIE, movie);

        return PagePath.MOVIE_PAGE;
    }
}
