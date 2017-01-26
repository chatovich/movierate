package com.chatovich.movie.dao;

import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.exception.RollbackFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "feedback" that its implementations should realize
 */
public interface FeedbackDAO {

    void save(Feedback feedback) throws DAOFailedException;
    List <Feedback> findFeedbacksByMovieId (long id) throws DAOFailedException;
    List<Feedback> findFeedbacksByStatus (String status) throws DAOFailedException;
    Feedback findEntityById (long id) throws DAOFailedException;
    void updateFeedbackStatus (boolean isAccepted, long id) throws DAOFailedException;
    List <Feedback> findFeedbacksByUserId (long id) throws DAOFailedException;
    List<Feedback> findUserMarks (long id) throws DAOFailedException;
    boolean checkLikeExists (long id_user, long id_feedback) throws DAOFailedException;
    int updateLikes(long id_user, long id_feedback, int likes) throws DAOFailedException, RollbackFailedException;
    int findFeedbackLikes(long id_feedback) throws DAOFailedException;
    List<Feedback> findLatestFeedbacks() throws DAOFailedException;
    long findFeedbackOwner(long id_feedback) throws DAOFailedException;

}
