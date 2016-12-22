package com.movierate.movie.service;

import com.movierate.movie.dao.impl.FeedbackDAOImpl;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Class that encapsulates logic connected with entity "feedback" and represents intermediate layer between database and client
 */
public class FeedbackService {

    public void createFeedback (User user, long id_movie, String text, int mark) throws DAOFailedException {

        Feedback feedback = new Feedback();
        Movie movie = new Movie();
        movie.setId(id_movie);
        feedback.setMovie(movie);
        feedback.setUser(user);
        feedback.setText(text);
        feedback.setMark(mark);
        feedback.setCreatingDate(LocalDate.now());
        FeedbackDAOImpl feedbackDAOImpl = new FeedbackDAOImpl();
        feedbackDAOImpl.save(feedback);
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

    public int addLike(long id_user, long id_feedback, int likes) throws ServiceException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        int likesCount = 0;
        try {
            if (!feedbackDAO.checkLikeExists(id_user,id_feedback)){
                likesCount = feedbackDAO.updateLikes(id_user,id_feedback, likes);

            }
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return likesCount;
    }
}
