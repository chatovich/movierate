package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Country;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.CountryService;
import com.movierate.movie.service.GenreService;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.type.Profession;
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

        String id_movie = request.getParameter(Parameters.ID_MOVIE);
        MovieService movieService = new MovieService();
        Movie movie;
        try {
            movie = movieService.findMovieById(Integer.parseInt(id_movie));
            ParticipantService participantService = new ParticipantService();
            GenreService genreService = new GenreService();
            CountryService countryService = new CountryService();
            List<Participant> actors = participantService.getParticipants(String.valueOf(Profession.ACTOR).toLowerCase());
            List<Participant> directors = participantService.getParticipants(String.valueOf(Profession.DIRECTOR).toLowerCase());
            List<Genre> genres = genreService.getGenres();
            List<Country> countries = countryService.getCountries();
            List<Participant> movieActors = new ArrayList<>();
            List<Participant> movieDirectors = new ArrayList<>();
            for (Participant participant : movie.getMovieParticipants()) {
                if ("actor".equals(String.valueOf(participant.getProfession()).toLowerCase())) {
                    movieActors.add(participant);
                } else {
                    movieDirectors.add(participant);
                }
            }
            request.setAttribute("movieActors", movieActors);
            request.setAttribute("movieDirectors", movieDirectors);
            request.setAttribute("actors", actors);
            request.setAttribute("directors", directors);
            request.setAttribute("genres", genres);
            request.setAttribute("countries", countries);
            request.setAttribute("movie",movie);
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, "Impossible to get info for movie update: "+e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.UPDATE_MOVIE_PAGE;
    }
}
