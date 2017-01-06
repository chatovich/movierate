package com.movierate.movie.service;

import com.movierate.movie.dao.FeedbackDAO;
import com.movierate.movie.dao.impl.FeedbackDAOImpl;
import com.movierate.movie.dao.impl.MovieDAOImpl;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.RollbackFailedException;
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

    /**
     * creates new object 'feedback'
     * @param user user who left feedback
     * @param id_movie movie id
     * @param text text of the feedback
     * @param mark mark that user left to movie
     * @throws ServiceException if DAOFailedException is thrown
     */
    public void createFeedback (User user, long id_movie, String text, int mark) throws ServiceException {

        Feedback feedback = new Feedback();
        Movie movie = new Movie();
        movie.setId(id_movie);
        feedback.setMovie(movie);
        feedback.setUser(user);
        feedback.setText(text);
        feedback.setMark(mark);
        feedback.setCreatingDate(LocalDate.now());
        FeedbackDAOImpl feedbackDAOImpl = new FeedbackDAOImpl();
        try {
            feedbackDAOImpl.save(feedback);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * gets feedbacks of specified status
     * @param status new, rejected, published
     * @return list of feedbacks
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Feedback> getFeedbacksByStatus(String status) throws ServiceException {

        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        List<Feedback> feedbacks = new ArrayList<>();
        try {
            feedbacks = feedbackDAO.findFeedbacksByStatus(status);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        Collections.sort(feedbacks, (a,b)->a.getCreatingDate().compareTo(b.getCreatingDate()));
        return feedbacks;
    }

    /**
     * gets feedback by id
     * @param id feedback id
     * @return feedback
     * @throws ServiceException if DAOFailedException is thrown
     */
    public Feedback getFeedback (String id) throws ServiceException {

        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        Feedback feedback;
        try {
            feedback = feedbackDAO.findEntityById(Long.parseLong(id));
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return feedback;

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

    /**
     * adds like to feedback
     * @param id_user id of the user who left like
     * @param id_feedback feedback id
     * @param likes likes quantity of this feedback
     * @return new likes' quantity
     * @throws ServiceException if DAOFailedException is thrown
     */
    public int addLike(long id_user, long id_feedback, int likes) throws ServiceException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        int likesCount = 0;
        try {
            if (!feedbackDAO.checkLikeExists(id_user,id_feedback)){
                likesCount = feedbackDAO.updateLikes(id_user,id_feedback, likes);
            } else {
                likesCount = feedbackDAO.findFeedbackLikes(id_feedback);
            }
        } catch (DAOFailedException |RollbackFailedException e) {
            throw new ServiceException(e);
        }
        return likesCount;
    }

    /**
     * finds 5 latest feedbacks for loading main page
     * @return list with 5 feedbacks
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Feedback> findLatestFeedbacks() throws ServiceException {
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        List<Feedback> feedbacks;
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
            throw new ServiceException(e);
        }
        return feedbacks;
    }
}
