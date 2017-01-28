package com.chatovich.movie.service.impl;

import com.chatovich.movie.dao.DAOFactory;
import com.chatovich.movie.dao.IFeedbackDAO;
import com.chatovich.movie.dao.impl.FeedbackDAOImpl;
import com.chatovich.movie.dao.impl.MovieDAOImpl;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.exception.RollbackFailedException;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.util.RatingCalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that encapsulates logic connected with entity "feedback" and represents intermediate layer between database and client
 */
public class FeedbackServiceImpl implements IFeedbackService {

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
        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        try {
            feedbackDAO.save(feedback);
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

        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        List<Feedback> feedbacks;
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

        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        Feedback feedback;
        try {
            feedback = feedbackDAO.findEntityById(Long.parseLong(id));
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return feedback;

    }

    /**
     * updates feedback status (published or rejected), if published - updates movie rating
     * @param isAccepted true - if admin accepted the feedback, false - if rejected
     * @param id id of the feedback
     * @throws ServiceException if method invokes DaoFailedException
     */
    public void updateFeedbackStatus (boolean isAccepted, String id) throws ServiceException {
        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        long id_feedback = Long.parseLong(id);
        try {
            feedbackDAO.updateFeedbackStatus(isAccepted, id_feedback);
            if (isAccepted) {
                Feedback feedback = feedbackDAO.findEntityById(id_feedback);
                List<Feedback> movieFeedbacks = feedbackDAO.findFeedbacksByMovieId(feedback.getMovie().getId());
                RatingCalculator ratingCalculator = new RatingCalculator();
                double movieRating = ratingCalculator.calcMovieRating(movieFeedbacks);
                MovieDAOImpl movieDao = new MovieDAOImpl();
                movieDao.updateMovieRating(movieRating, feedback.getMovie().getId());
            }
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    /**
     * adds like to feedback, first checking whether this user has already liked the feedback or this is his own feedback
     * @param id_user id of the user who left like
     * @param id_feedback feedback id
     * @param likes likes quantity of this feedback
     * @return new likes' quantity
     * @throws ServiceException if DAOFailedException is thrown
     */
    public int addLike(long id_user, long id_feedback, int likes) throws ServiceException {
        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        int likesCount = 0;
        try {
            if ((!feedbackDAO.checkLikeExists(id_user,id_feedback))&&(id_user!=feedbackDAO.findFeedbackOwner(id_feedback))){
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
        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
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
