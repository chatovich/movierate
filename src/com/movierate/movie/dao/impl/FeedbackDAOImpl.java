package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.FeedbackDAO;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.RollbackFailedException;
import com.movierate.movie.type.FeedbackStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "feedbacks"
 */
public class FeedbackDAOImpl implements FeedbackDAO, DAO {

    private static final Logger LOGGER = LogManager.getLogger(FeedbackDAOImpl.class);
    private static final String SQL_UPDATE_FEEDBACK_STATUS = "UPDATE feedbacks SET status=? WHERE id_feedback=?";
    private static final String SQL_FIND_LIKE = "SELECT user, feedback FROM likes WHERE user=? AND feedback=?";
    private static final String SQL_ADD_USER_LIKES = "INSERT INTO likes (user, feedback) VALUES (?,?)";
    private static final String SQL_FIND_FEEDBACK_LIKES = "SELECT likes FROM feedbacks WHERE id_feedback=?";
    private static final String SQL_FIND_LATEST_FEEDBACK = "SELECT id_feedback, text, to_movie, movies.title FROM feedbacks " +
            "JOIN movies ON movies.id_movie=feedbacks.to_movie WHERE status='published' ORDER BY creating_date DESC LIMIT 5;";
    private static final String SQL_FIND_FEEDBACKS_OF_USER = "SELECT id_feedback, text,mark, likes, creating_date, status," +
            "movies.id_movie, movies.title FROM feedbacks JOIN movies ON movies.id_movie=feedbacks.to_movie WHERE from_user=? ORDER BY creating_date DESC";
    private static final String SQL_FIND_MARKS_OF_USER = "SELECT id_feedback, mark, movies.id_movie, movies.title, movies.rating FROM feedbacks " +
            "JOIN movies ON movies.id_movie=feedbacks.to_movie WHERE from_user=?";
    private static final String SQL_FIND_FEEDBACKS_OF_MOVIE = "SELECT id_feedback, text,mark, from_user, likes, creating_date, status," +
            " users.login, users.photo FROM feedbacks JOIN users ON from_user=id_user WHERE status='published' AND to_movie=? ORDER BY creating_date DESC;";
    private static final String SQL_FIND_FEEDBACK_BY_ID = "SELECT id_feedback, text, from_user, to_movie, likes, creating_date, status," +
            " users.login, movies.title, mark FROM feedbacks JOIN users ON from_user=id_user JOIN movies ON to_movie=id_movie WHERE id_feedback=?";
    private static final String SQL_FIND_ALL_FEEDBACKS = "SELECT id_feedback, from_user, to_movie FROM feedbacks";
    private static final String SQL_FIND_FEEDBACKS_BY_STATUS = "SELECT id_feedback, from_user, to_movie, movies.title, creating_date, users.login, " +
            "movies.title FROM feedbacks JOIN users ON from_user=id_user JOIN movies ON to_movie=id_movie WHERE status=?";
    private static final String SQL_UPDATE_LIKES_IN_FEEDBACKS = "UPDATE feedbacks SET likes=? WHERE id_feedback=?";
    private static final String SQL_SAVE_FEEDBACK = "INSERT INTO feedbacks (text,from_user,to_movie, creating_date, mark) VALUES (?,?,?,?,?)";


    /**
     * finds all feedbacks of the movie by movie id
     * @param id id of the movie
     * @return list containing feedbacks of the movie
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List <Feedback> findFeedbacksByMovieId(long id) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_FEEDBACKS_OF_MOVIE)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setText(rs.getString("text"));
                feedback.setLikes(rs.getInt("likes"));
                feedback.setMark(rs.getInt("mark"));
                feedback.setStatus(FeedbackStatus.valueOf((rs.getString("status")).toUpperCase()));
                feedback.setCreatingDate(LocalDate.parse(rs.getString("creating_date")));
                feedback.setUser(new User(rs.getInt("from_user"), rs.getString("users.login"), rs.getString("users.photo")));
                feedbacksList.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find feedbacks by movie id: "+e.getMessage());
        }
        return feedbacksList;
    }

    /**
     * saves feedback in the db
     * @param feedback feedback object that should be placed into database
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public void save(Feedback feedback) throws DAOFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try(
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_SAVE_FEEDBACK)){
            st.setString(1, feedback.getText());
            st.setLong(2, feedback.getUser().getId());
            st.setLong(3, feedback.getMovie().getId());
            st.setString(4, feedback.getCreatingDate().toString());
            st.setInt(5, feedback.getMark());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to save feedback into db: "+e.getMessage());
        }
    }

    /**
     * finds all feedbacks of the specified status
     * @param status status of feedbacks (new, published, rejected)
     * @return list with feedbacks of the specified status
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Feedback> findFeedbacksByStatus(String status) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_FEEDBACKS_BY_STATUS)){
            preparedStatement.setString(1, status);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setUser(new User(rs.getLong("from_user"), rs.getString("users.login")));
                feedback.setMovie(new Movie(rs.getLong("to_movie"), rs.getString("movies.title")));
                feedback.setCreatingDate(LocalDate.parse(rs.getString("creating_date")));
                feedbacksList.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to get feedbacks: "+e.getMessage());
        }
        return feedbacksList;
    }

    /**
     * finds feedback in the db by its id
     * @param id id of the feedback to find
     * @return feedback found in the db
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public Feedback findEntityById(long id) throws DAOFailedException {
        Feedback feedback = new Feedback();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_FEEDBACK_BY_ID)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setText(rs.getString("text"));
                feedback.setLikes(rs.getInt("likes"));
                feedback.setStatus(FeedbackStatus.valueOf((rs.getString("status")).toUpperCase()));
                feedback.setCreatingDate(LocalDate.parse(rs.getString("creating_date")));
                feedback.setUser(new User(rs.getInt("from_user"), rs.getString("users.login")));
                feedback.setMovie(new Movie(rs.getLong("to_movie"), rs.getString("movies.title")));
                feedback.setMark(rs.getInt("mark"));
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find a feedback: "+e.getMessage());
        }
        return feedback;
    }

    /**
     * updates feedback status to published or rejected depending on admin's decision
     * @param isAccepted whether feedback was accepted by admin or not
     * @param id id of the feedback
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public void updateFeedbackStatus(boolean isAccepted, long id) throws DAOFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_UPDATE_FEEDBACK_STATUS)){
            if (isAccepted){
                st.setString(1,FeedbackStatus.PUBLISHED.getStatus());
            } else {
                st.setString(1,FeedbackStatus.REJECTED.getStatus());
            }
            st.setLong(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find a feedback: "+e.getMessage());
        }
    }

    /**
     * finds all feedbacks written by the user
     * @param id user id
     * @return list of user feedbacks
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Feedback> findFeedbacksByUserId(long id) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_FEEDBACKS_OF_USER)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setText(rs.getString("text"));
                feedback.setLikes(rs.getInt("likes"));
                feedback.setMark(rs.getInt("mark"));
                feedback.setStatus(FeedbackStatus.valueOf((rs.getString("status")).toUpperCase()));
                feedback.setCreatingDate(LocalDate.parse(rs.getString("creating_date")));
                feedback.setUser(new User(id));
                feedback.setMovie(new Movie(rs.getLong("id_movie"), rs.getString("title")));
                feedbacksList.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find user feedbacks: "+e.getMessage());
        }
        return feedbacksList;
    }

    /**
     * finds all marks that user gave to movies
     * @param id user id
     * @return list of user feedbacks including marks
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Feedback> findUserMarks(long id) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_MARKS_OF_USER)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setMark(rs.getInt("mark"));
                feedback.setMovie(new Movie(rs.getLong("id_movie"), rs.getString("title"), rs.getDouble("rating")));
                feedbacksList.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to get feedbacks: "+e.getMessage());
        }
        return feedbacksList;
    }

    /**
     * checks whether this user has already liked this feedback
     * @param id_user user id
     * @param id_feedback feedback id
     * @return true if user has already liked this feedback, otherwise - false
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public boolean checkLikeExists(long id_user, long id_feedback) throws DAOFailedException {
        boolean likeExists = false;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_LIKE)){
            st.setLong(1, id_user);
            st.setLong(2, id_feedback);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                likeExists = true;
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to check feedback's likes: "+e.getMessage());
        }
        return likeExists;
    }

    /**
     * increments likes' quantity in feedbacks table and in likes table
     * @param id_user user id
     * @param id_feedback feedback id
     * @param likes previous value of likes quantity
     * @return new value of likes quantity
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public int updateLikes(long id_user, long id_feedback, int likes) throws DAOFailedException, RollbackFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement stFeedback = null;
        PreparedStatement stLikes = null;
        try{
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            try{
                stFeedback = connection.prepareStatement(SQL_UPDATE_LIKES_IN_FEEDBACKS);
                stFeedback.setInt(1,likes+1);
                stFeedback.setLong(2,id_feedback);
                stFeedback.executeUpdate();
            } finally {
                close(stFeedback);
            }
            try{
                stLikes = connection.prepareStatement(SQL_ADD_USER_LIKES);
                stLikes.setLong(1, id_user);
                stLikes.setLong(2, id_feedback);
                stLikes.executeUpdate();
            } finally {
                close(stLikes);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RollbackFailedException("Impossible to rollback: "+e.getMessage());
            }
            throw new DAOFailedException("Impossible to update likes: "+e.getMessage());
        }
        return likes+1;
    }

    /**
     * finds the quantity of feedback's likes
     * @param id_feedback feedback id
     * @return feedback's likes' quantity
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public int findFeedbackLikes(long id_feedback) throws DAOFailedException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        int likes = 0;
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_FEEDBACK_LIKES)){
            st.setLong(1, id_feedback);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                likes = rs.getInt("likes");
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find feedbacks' likes: "+e.getMessage());
        }
        return likes;
    }

    /**
     * Finds 5 latest feedbacks for main page
     * @return list with feedbacks
     * @throws DAOFailedException if method invokes DaoFailedException
     */
    @Override
    public List<Feedback> findLatestFeedbacks() throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(SQL_FIND_LATEST_FEEDBACK);
            while (rs.next()){
                Feedback feedback = new Feedback();
                feedback.setId(rs.getLong("id_feedback"));
                feedback.setMovie(new Movie(rs.getLong("to_movie"), rs.getString("movies.title")));
                feedback.setText(rs.getString("text"));
                feedbacksList.add(feedback);
            }

        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find latest feedbacks: "+e.getMessage());
        }
        return feedbacksList;
    }
}
