package com.chatovich.movie.command;

import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.CountryService;
import com.chatovich.movie.type.Profession;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Country;
import com.chatovich.movie.entity.Genre;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.service.GenreService;
import com.chatovich.movie.service.ParticipantService;
import com.chatovich.movie.util.QueryUtil;
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
