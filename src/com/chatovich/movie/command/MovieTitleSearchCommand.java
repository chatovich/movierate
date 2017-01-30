package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IMovieService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * command for searching movie by its title
 */
public class MovieTitleSearchCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        String title = request.getParameter(Parameters.PARAM_TITLE);
        IMovieService movieService = ServiceFactory.getInstance().getMovieService();
        int pageQuantity = 0;
        int page = 1;
        if (request.getParameter(Parameters.PAGE)!=null){
            page = Integer.parseInt(request.getParameter(Parameters.PAGE));
        }
        List<Movie> movies;
        try {
            movies = movieService.findMovieByTitle(title, (page-1)*Parameters.MOVIES_PER_PAGE, Parameters.MOVIES_PER_PAGE);
            request.setAttribute(Parameters.MOVIES, movies);
            pageQuantity = (int)Math.ceil(((double) movieService.getMovieQuantity())/Parameters.MOVIES_PER_PAGE);
            request.setAttribute(Parameters.PAGE_QUANTITY, pageQuantity);
            request.setAttribute(Parameters.CURRENT_PAGE, page);
            if (movies.isEmpty()){
                request.setAttribute(Parameters.EMPTY_MOVIES_LIST, true);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MOVIE_LIST_PAGE;    }
}
