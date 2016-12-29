package com.movierate.movie.service;

import com.movierate.movie.dao.FeedbackDAO;
import com.movierate.movie.dao.impl.FeedbackDAOImpl;
import com.movierate.movie.dao.impl.MovieDAOImpl;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * Updates feedback status (published or rejected), if published - updates movie rating
     * @param isAccepted true - if admin accepted the feedback, false - if rejected
     * @param id id of the feedback
     * @throws ServiceException if method invokes DaoFailedException
     */
    public void updateFeedbackStatus (boolean isAccepted, String id) throws ServiceException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        long id_feedback = Long.parseLong(id);
        try {
            feedbackDAO.updateFeedbackStatus(isAccepted, id_feedback);
            if (isAccepted) {
                Feedback feedback = feedbackDAO.findEntityById(id_feedback);
                List<Feedback> movieFeedbacks = feedbackDAO.findFeedbacksByMovieId(feedback.getMovie().getId());
                int count = 0;
                int sum = 0;
                for (Feedback movieFeedback : movieFeedbacks) {
                    sum+=movieFeedback.getMark();
                    count++;
                }
                double movieRating = new BigDecimal(sum / count).setScale(2, RoundingMode.UP).doubleValue();
                MovieDAOImpl movieDao = new MovieDAOImpl();
                movieDao.updateMovieRating(movieRating, feedback.getMovie().getId());
            }
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    public int addLike(long id_user, long id_feedback, int likes) throws ServiceException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        int likesCount = 0;
        try {
            if (!feedbackDAO.checkLikeExists(id_user,id_feedback)){
                likesCount = feedbackDAO.updateLikes(id_user,id_feedback, likes);
            } else {
                likesCount = feedbackDAO.findFeedbackLikes(id_feedback);
            }
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return likesCount;
    }

    public List<Feedback> findLatestFeedbacks() throws ServiceException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            feedbacks = feedbackDAO.findLatestFeedbacks();
            for (Feedback feedback : feedbacks) {
                String text = feedback.getText();
                if (text.length()>100) {
                    text = text.substring(0, 100);
                    text = text + "...";
                }
                feedback.setText(text);
            }
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return feedbacks;
    }
}
