package com.chatovich.movie.command;

import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.*;
import com.chatovich.movie.service.impl.CountryServiceImpl;
import com.chatovich.movie.type.Profession;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Country;
import com.chatovich.movie.entity.Genre;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.service.impl.GenreServiceImpl;
import com.chatovich.movie.service.impl.MovieServiceImpl;
import com.chatovich.movie.service.impl.ParticipantServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * command that gets the info about the film admin wanna update and all needed list of participants, genres and countries
 */
public class GetMovieInfoForUpdateCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetMovieInfoForUpdateCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        String id_movie = request.getParameter(Parameters.ID_MOVIE);
        if (request.getParameter(Parameters.ACTION).equals(Parameters.DELETE)){
            try {
                movieServiceImpl.deleteMovie(Long.parseLong(id_movie));
                request.setAttribute(Parameters.MOVIE_DELETED, true);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                return PagePath.ERROR_PAGE;
            }
            return PagePath.USER_PAGE;
        }

        Movie movie;
        IParticipantService participantServiceImpl = ServiceFactory.getInstance().getParticipantService();
        IGenreService genreServiceImpl = ServiceFactory.getInstance().getGenreService();
        ICountryService countryServiceImpl = ServiceFactory.getInstance().getCountryService();
        try {
            movie = movieServiceImpl.findMovieById(Integer.parseInt(id_movie));
            List<Participant> actors = participantServiceImpl.getParticipants(String.valueOf(Profession.ACTOR).toLowerCase());
            List<Participant> directors = participantServiceImpl.getParticipants(String.valueOf(Profession.DIRECTOR).toLowerCase());
            List<Genre> genres = genreServiceImpl.getGenres();
            List<Country> countries = countryServiceImpl.getCountries();
            List<Participant> movieActors = new ArrayList<>();
            List<Participant> movieDirectors = new ArrayList<>();
            for (Participant participant : movie.getMovieParticipants()) {
                if (Parameters.ACTOR.equals(String.valueOf(participant.getProfession()).toLowerCase())) {
                    movieActors.add(participant);
                } else {
                    movieDirectors.add(participant);
                }
            }
            request.setAttribute(Parameters.MOVIE_ACTORS, movieActors);
            request.setAttribute(Parameters.MOVIE_DIRECTORS, movieDirectors);
            request.setAttribute(Parameters.ACTORS, actors);
            request.setAttribute(Parameters.DIRECTORS, directors);
            request.setAttribute(Parameters.GENRES, genres);
            request.setAttribute(Parameters.COUNTRIES, countries);
            request.setAttribute(Parameters.MOVIE,movie);
        }  catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.UPDATE_MOVIE_PAGE;
    }
}
