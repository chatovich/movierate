package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.dao.impl.MovieDAOImpl;
import com.movierate.movie.entity.Movie;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class that serves the request to get movies by genre
 */
public class GetMoviesByGenreCommand implements ICommand {

    private static final int MOVIES_PER_PAGE = 3;
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_GENRE = "genre";
    private static final String ATTR_MOVIES = "movies";
    private static final String ATTR_PAGE_QUANTITY = "pageQuantity";
    private static final String ATTR_PAGE_CURRENT = "currentPage";

    @Override
    public String execute(HttpServletRequest request) {
        String genre = request.getParameter(PARAM_GENRE);
        int page = 1;
        if (request.getParameter(PARAM_PAGE)!=null){
            page = Integer.parseInt(request.getParameter(PARAM_PAGE));
        }
        MovieDAOImpl movieDAOImpl = new MovieDAOImpl();
        List <Movie> movies = movieDAOImpl.findMovieByGenre(genre, (page-1)*MOVIES_PER_PAGE, MOVIES_PER_PAGE);
        int movieQuantity = movieDAOImpl.getPageQuantity();
        int pageQuantity = (int)Math.ceil(((double)movieQuantity)/MOVIES_PER_PAGE);

        request.setAttribute(ATTR_MOVIES, movies);
        request.setAttribute(ATTR_PAGE_QUANTITY, pageQuantity);
        request.setAttribute(ATTR_PAGE_CURRENT, page);
        request.setAttribute(PARAM_GENRE, genre);
        return PagePath.MOVIE_LIST_PAGE;
    }
}
