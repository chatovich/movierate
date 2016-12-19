package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * picks data entered by user to find movies according to user request
 */
public class FilteredMovieSearchCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(FilteredMovieSearchCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        int pageQuantity = 0;
        int page = 1;
        List<Movie> movies;
        Map<String, String[]> parameters = request.getParameterMap();
        System.out.println(request.getParameter("genre"));
//        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
//            System.out.println(entry.getKey()+" - "+entry.getValue()[0]);
//        }
        if (request.getParameter(Parameters.PAGE)!=null){
            page = Integer.parseInt(request.getParameter(Parameters.PAGE));
        }
        MovieService movieService = new MovieService();
        try {
            movies = movieService.filteredMovieSearch(parameters, (page-1)*Parameters.MOVIES_PER_PAGE, Parameters.MOVIES_PER_PAGE);
            pageQuantity = (int)Math.ceil(((double)movieService.getMovieQuantity())/Parameters.MOVIES_PER_PAGE);
            request.setAttribute(Parameters.MOVIES, movies);
            request.setAttribute(Parameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(Parameters.CURRENT_PAGE, page);
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                switch (entry.getKey()){
                    case Parameters.GENRE: request.setAttribute(Parameters.GENRE, entry.getValue()[0]);
                        break;
                    case Parameters.COUNTRY: request.setAttribute(Parameters.COUNTRY, entry.getValue()[0]);
                        break;
                    case Parameters.YEAR: request.setAttribute(Parameters.YEAR, entry.getValue()[0]);
                        break;
                    case Parameters.PARTICIPANT: request.setAttribute(Parameters.PARTICIPANT, entry.getValue()[0]);
                        break;

                }
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.MOVIE_LIST_PAGE;
    }
}
