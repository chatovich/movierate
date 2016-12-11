package com.movierate.movie.service;

import com.movierate.movie.dao.impl.FeedbackDAOImpl;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Class that encapsulates logic connected with entity "feedback" and represents intermediate layer between database and client
 */
public class FeedbackService {

    public boolean createFeedback (User user, long id_movie, String text){

        boolean isCreated = false;
        Feedback feedback = new Feedback();
        Movie movie = new Movie();
        movie.setId(id_movie);
        feedback.setMovie(movie);
        feedback.setUser(user);
        feedback.setText(text);
        feedback.setCreatingDate(LocalDate.now());
        FeedbackDAOImpl feedbackDAOImpl = new FeedbackDAOImpl();
        isCreated = feedbackDAOImpl.save(feedback);
        return isCreated;

    }

    public List<Feedback> getFeedbacksByStatus(String status) throws DAOFailedException {

        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        List<Feedback> feedbacks = feedbackDAO.findFeedbacksByStatus(status);
        Collections.sort(feedbacks, (a,b)->a.getCreatingDate().compareTo(b.getCreatingDate()));
        return feedbacks;
    }

    public Feedback getFeedback (String id) throws DAOFailedException {

        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        return feedbackDAO.findEntityById(Long.parseLong(id));

    }

    public void updateFeedbackStatus (boolean isAccepted, String id) throws DAOFailedException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        feedbackDAO.updateFeedbackStatus(isAccepted, Long.parseLong(id));
    }
}
