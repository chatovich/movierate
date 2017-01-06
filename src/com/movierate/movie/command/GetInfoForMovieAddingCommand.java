package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Country;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.CountryService;
import com.movierate.movie.service.GenreService;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.type.Profession;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * command that gets the data needed for adding a movie from tha db (actors and directors list, country list, genres list)
 */
public class GetInfoForMovieAddingCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetInfoForMovieAddingCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        ParticipantService participantService = new ParticipantService();
        GenreService genreService = new GenreService();
        CountryService countryService = new CountryService();
        try {
            List<Participant> actors = participantService.getParticipants(String.valueOf(Profession.ACTOR).toLowerCase());
            List<Participant> directors = participantService.getParticipants(String.valueOf(Profession.DIRECTOR).toLowerCase());
            List<Genre> genres = genreService.getGenres();
            List<Country> countries = countryService.getCountries();
            request.setAttribute(Parameters.ACTORS, actors);
            request.setAttribute(Parameters.DIRECTORS, directors);
            request.setAttribute(Parameters.GENRES, genres);
            request.setAttribute(Parameters.COUNTRIES, countries);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.ADD_MOVIE_PAGE;
    }
}
