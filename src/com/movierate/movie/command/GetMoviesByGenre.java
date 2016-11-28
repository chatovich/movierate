package com.movierate.movie.command;

import com.movierate.movie.dao.MovieDAO;
import com.movierate.movie.entity.Movie;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class that serves the request to get movies by genre
 */
public class GetMoviesByGenre implements ICommand {

    public static final int MOVIES_PER_PAGE = 3;

    @Override
    public String execute(HttpServletRequest request) {
        String genre = request.getParameter("genre");
        int page = 1;
        if (request.getParameter("page")!=null){
            page = Integer.parseInt(request.getParameter("page"));
        }
        MovieDAO movieDAO = new MovieDAO();
        List <Movie> movies = movieDAO.findMovieByGenre(genre, (page-1)*MOVIES_PER_PAGE, MOVIES_PER_PAGE);
        int movieQuantity = movieDAO.getPageQuantity();
        int pageQuantity = (int)Math.ceil(((double)movieQuantity)/MOVIES_PER_PAGE);

        request.setAttribute("movies", movies);
        request.setAttribute("pageQuantity", pageQuantity);
        request.setAttribute("currentPage", page);
        request.setAttribute("genre", genre);
        return "jsp/movie/movies_list.jsp";
    }
}
