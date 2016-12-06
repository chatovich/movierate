package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.entity.Country;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.service.CountryService;
import com.movierate.movie.service.GenreService;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.type.Profession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * command that gets the data needed for adding a movie from tha db (actors and directors list, country list, genres list)
 */
public class GetInfoForMovieAddingCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {

        ParticipantService participantService = new ParticipantService();
        GenreService genreService = new GenreService();
        CountryService countryService = new CountryService();
        List<Participant> actors = participantService.getParticipants(String.valueOf(Profession.ACTOR).toLowerCase());
        List<Participant> directors = participantService.getParticipants(String.valueOf(Profession.DIRECTOR).toLowerCase());
        List<Genre> genres = genreService.getGenres();
        List<Country> countries = countryService.getCountries();
        request.setAttribute("actors", actors);
        request.setAttribute("directors", directors);
        request.setAttribute("genres", genres);
        request.setAttribute("countries", countries);
        request.setAttribute("add_movie", true);
        return PagePath.ADMIN_MAIN_PAGE;
    }
}
