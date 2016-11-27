package com.movierate.movie.command;

import com.movierate.movie.dao.MovieDAO;
import com.movierate.movie.entity.Movie;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class that serves the request to get movies by genre
 */
public class GetMoviesByGenre implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        String genre = request.getParameter("genre");
        MovieDAO movieDAO = new MovieDAO();
        List <Movie> movies = movieDAO.findMovieByGenre(genre);
        request.setAttribute("movies", movies);
        return "jsp/main/main.jsp";
    }
}
