package com.movierate.movie.service;

import com.movierate.movie.dao.*;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.Participant;

/**
 * Class that encapsulates logic connected with entity "movie" and represents intermediate layer between database and client
 */
public class MovieService {

    /**
     * creates a new object Movie using data gotten by dao
     * @param id id of the movie to create
     * @return created movie object
     */
    public Movie findMovieById(int id){
        Movie movie = new Movie();
        MovieDAO movieDAO = new MovieDAO();
        if (!movieDAO.findEntityById(id).isEmpty()){
            movie = movieDAO.findEntityById(id).get(0);
        }
        GenreDAO genreDAO = new GenreDAO();
        CountryDAO countryDAO = new CountryDAO();
        ParticipantDAO participantDAO = new ParticipantDAO();
        MarkDAO markDAO = new MarkDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        movie.setMovieGenres(genreDAO.findEntityById(id));
        movie.setMovieCountries(countryDAO.findEntityById(id));
        movie.setMovieParticipants(participantDAO.findEntityById(id));
        movie.setMovieMarks(markDAO.findEntityById(id));
        movie.setMovieFeedbacks(feedbackDAO.findEntityById(id));
        return movie;

    }
}
