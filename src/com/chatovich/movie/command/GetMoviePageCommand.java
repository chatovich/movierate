package com.chatovich.movie.command;

import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.service.MovieService;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that picks data needed for build a movie page
 */
public class GetMoviePageCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetMoviePageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        int id_movie = Integer.parseInt(request.getParameter(Parameters.ID_MOVIE));
        MovieService movieService = new MovieService();
        Movie movie = null;
        try {
            movie = movieService.findMovieById(id_movie);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.setAttribute(Parameters.MOVIE, movie);
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MOVIE_PAGE;
    }
}
