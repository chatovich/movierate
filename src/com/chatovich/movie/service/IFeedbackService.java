package com.chatovich.movie.service;

import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;

import java.util.List;

/**
 * interface for operations with entity "feedback"
 */
public interface IFeedbackService {

    void createFeedback (User user, long id_movie, String text, int mark) throws ServiceException;
    List<Feedback> getFeedbacksByStatus(String status) throws ServiceException;
    Feedback getFeedback (String id) throws ServiceException;
    void updateFeedbackStatus (boolean isAccepted, String id) throws ServiceException;
    int addLike(long id_user, long id_feedback, int likes) throws ServiceException;
    List<Feedback> findLatestFeedbacks() throws ServiceException;
}
