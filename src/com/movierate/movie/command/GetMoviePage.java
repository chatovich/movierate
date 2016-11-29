package com.movierate.movie.command;

import com.movierate.movie.dao.MovieDAO;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that picks data needed for build a movie page
 */
public class GetMoviePage implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {

        int id_movie = Integer.parseInt(request.getParameter("id"));
        MovieService movieService = new MovieService();
        Movie movie = movieService.getMovieById(id_movie);
        request.setAttribute("movie", movie);

        return "/jsp/movie/movie.jsp";
    }
}
