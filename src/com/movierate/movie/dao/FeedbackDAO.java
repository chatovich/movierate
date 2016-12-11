package com.movierate.movie.dao;

import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "feedback" that its implementations should realize
 */
public interface FeedbackDAO {

    boolean save(Feedback feedback);
    List <Feedback> findFeedbacksByMovieId (int id);
    List<Feedback> findFeedbacksByStatus (String status) throws DAOFailedException;
    Feedback findEntityById (long id) throws DAOFailedException;
    void updateFeedbackStatus (boolean isAccepted, long id) throws DAOFailedException;
}
