package com.movierate.movie.service;

import com.movierate.movie.dao.impl.*;
import com.movierate.movie.entity.Country;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.Participant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that encapsulates logic connected with entity "movie" and represents intermediate layer between database and client
 */
public class MovieService {

    /**
     * creates a new object Movie using data gotten by dao
     * @param id id of the movie to save
     * @return created movie object
     */
    public Movie findMovieById(int id){
        Movie movie = new Movie();
        MovieDAOImpl movieDAOImpl = new MovieDAOImpl();
        if (!movieDAOImpl.findEntityById(id).isEmpty()){
            movie = movieDAOImpl.findEntityById(id).get(0);
        }
        GenreDAOImpl genreDAOImpl = new GenreDAOImpl();
        CountryDAOImpl countryDAOImpl = new CountryDAOImpl();
        ParticipantDAOImpl participantDAOImpl = new ParticipantDAOImpl();
        MarkDAOImpl markDAOImpl = new MarkDAOImpl();
        FeedbackDAOImpl feedbackDAOImpl = new FeedbackDAOImpl();

        movie.setMovieGenres(genreDAOImpl.findGenresByMovieId(id));
        movie.setMovieCountries(countryDAOImpl.findEntityById(id));
        movie.setMovieParticipants(participantDAOImpl.findParticipantsByMovieId(id));
        movie.setMovieMarks(markDAOImpl.findEntityById(id));
        movie.setMovieFeedbacks(feedbackDAOImpl.findEntityById(id));
        return movie;

    }

    public void createMovie(Map<String,String[]> parameters, String path){

        Movie movie = new Movie();
        List<Genre> genres = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        CountryDAOImpl countryDAO = new CountryDAOImpl();
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        movie.setPoster(path);
        movie.setAdding_date(LocalDate.now());
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            switch (entry.getKey()){
                case "title": movie.setTitle(entry.getValue()[0]);
                    break;
                case "year": movie.setYear(Integer.parseInt(entry.getValue()[0]));
                    break;
                case "plot": movie.setPlot(entry.getValue()[0]);
                    break;
                case "duration": movie.setDuration(Integer.parseInt(entry.getValue()[0]));
                    break;
                case "genre":
                    for (String s : entry.getValue()) {
                        genres.add(genreDAO.findEntityByName(s));
                    }
                    break;
                case "country":
                    for (String s : entry.getValue()) {
                        countries.add(countryDAO.findEntityByName(s));
                    }
                    break;
                case "actor":
                    for (String s : entry.getValue()) {
                        participants.add(participantDAO.findEntityByName(s));
                    }
                    break;
                case "director":
                    for (String s : entry.getValue()) {
                        participants.add(participantDAO.findEntityByName(s));
                    }
                    break;
            }
        }
        movie.setMovieGenres(genres);
        movie.setMovieCountries(countries);
        movie.setMovieParticipants(participants);
        System.out.println(movie);
        MovieDAOImpl movieDAO = new MovieDAOImpl();
        movieDAO.save(movie);


    }
}
