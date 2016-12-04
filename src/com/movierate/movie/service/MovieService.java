package com.movierate.movie.service;

import com.movierate.movie.dao.impl.*;
import com.movierate.movie.entity.Movie;

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

        movie.setMovieGenres(genreDAOImpl.findEntityById(id));
        movie.setMovieCountries(countryDAOImpl.findEntityById(id));
        movie.setMovieParticipants(participantDAOImpl.findEntityById(id));
        movie.setMovieMarks(markDAOImpl.findEntityById(id));
        movie.setMovieFeedbacks(feedbackDAOImpl.findEntityById(id));
        return movie;

    }
}
