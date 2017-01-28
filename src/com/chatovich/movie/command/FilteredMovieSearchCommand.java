package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IMovieService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.MovieServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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
        if (request.getParameter(Parameters.PAGE)!=null){
            page = Integer.parseInt(request.getParameter(Parameters.PAGE));
        }
        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        try {
            movies = movieServiceImpl.filteredMovieSearch(parameters, (page-1)*Parameters.MOVIES_PER_PAGE, Parameters.MOVIES_PER_PAGE);
            pageQuantity = (int)Math.ceil(((double) movieServiceImpl.getMovieQuantity())/Parameters.MOVIES_PER_PAGE);
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
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        if (movies.isEmpty()){
            request.setAttribute(Parameters.EMPTY_MOVIES_LIST, true);
        }
        return PagePath.MOVIE_LIST_PAGE;
    }
}
