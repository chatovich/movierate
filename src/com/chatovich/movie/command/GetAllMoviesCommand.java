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

/**
 * command that gets all movies from the db
 */
public class GetAllMoviesCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetAllMoviesCommand.class);


    @Override
    public String execute(HttpServletRequest request) {

        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        List<Movie> movies;
        try {
            movies = movieServiceImpl.getAllMovies();
            request.setAttribute(Parameters.CHOOSE_MOVIE, true);
            request.setAttribute(Parameters.MOVIES, movies);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }

        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
