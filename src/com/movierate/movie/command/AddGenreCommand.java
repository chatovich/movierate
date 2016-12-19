package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.dao.impl.GenreDAOImpl;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.GenreService;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * command that picks the data entered by admin to add a new movie genre into the db
 */
public class AddGenreCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(AddGenreCommand.class);
    public static final String PARAM_GENRE = "genre";

    @Override
    public String execute(HttpServletRequest request) {

        String genre = request.getParameter(PARAM_GENRE);
        GenreService genreService = new GenreService();

        try{
            genreService.createGenre(genre);
            request.setAttribute("genreAdded", true);

        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, "Genre adding failed: "+e.getMessage());
            request.setAttribute("genreAdded", false);
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.ADMIN_MAIN_PAGE;

    }
}
