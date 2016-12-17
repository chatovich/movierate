package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.FeedbackDAO;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
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

    public static final Logger LOGGER = LogManager.getLogger(FeedbackDAOImpl.class);
    public static final String SQL_UPDATE_FEEDBACK_STATUS = "UPDATE feedbacks SET status=? WHERE id_feedback=?";
    public static final String SQL_FIND_FEEDBACKS_OF_USER = "SELECT id_feedback, text,mark, likes, creating_date, status," +
            "movies.id_movie, movies.title FROM feedbacks JOIN movies ON movies.id_movie=feedbacks.to_movie WHERE from_user=?";
    public static final String SQL_FIND_MARKS_OF_USER = "SELECT id_feedback, mark, movies.id_movie, movies.title FROM feedbacks " +
            "JOIN movies ON movies.id_movie=feedbacks.to_movie WHERE from_user=?";
    public static final String SQL_FIND_FEEDBACKS_OF_MOVIE = "SELECT id_feedback, text,mark, from_user, likes, creating_date, status," +
            " users.login, users.photo FROM feedbacks JOIN users ON from_user=id_user WHERE to_movie=?;";
    public static final String SQL_FIND_FEEDBACK_BY_ID = "SELECT id_feedback, text, from_user, to_movie, likes, creating_date, status," +
            " users.login, movies.title, mark FROM feedbacks JOIN users ON from_user=id_user JOIN movies ON to_movie=id_movie WHERE id_feedback=?";
    public static final String SQL_FIND_ALL_FEEDBACKS = "SELECT id_feedback, from_user, to_movie FROM feedbacks";
    public static final String SQL_FIND_FEEDBACKS_BY_STATUS = "SELECT id_feedback, from_user, to_movie, movies.title, creating_date, users.login, " +
            "movies.title FROM feedbacks JOIN users ON from_user=id_user JOIN movies ON to_movie=id_movie WHERE status=?";
    public static final String SQL_SAVE_FEEDBACK = "INSERT INTO feedbacks (text,from_user,to_movie, creating_date, mark) VALUES (?,?,?,?,?)";


    /**
     * finds all feedbacks of the movie by movie id
     * @param id id of the movie
     * @return list containing feedbacks of the movie
     */
    @Override
    public List <Feedback> findFeedbacksByMovieId(long id) {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_FEEDBACKS_OF_MOVIE);
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
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return feedbacksList;
    }

    /**
     *
     * @param feedback feedback object that should be placed into database
     * @return true if feedback was added to db successfully, false if not
     */
    @Override
    public void save(Feedback feedback) throws DAOFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_SAVE_FEEDBACK);
            st.setString(1, feedback.getText());
            st.setLong(2, feedback.getUser().getId());
            st.setLong(3, feedback.getMovie().getId());
            st.setString(4, feedback.getCreatingDate().toString());
            st.setInt(5, feedback.getMark());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to save feedback into db: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Feedback> findFeedbacksByStatus(String status) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;

        try  {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_FEEDBACKS_BY_STATUS);
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
        } finally {
            close(preparedStatement);
            connectionPool.releaseConnection(connection);
        }
        return feedbacksList;
    }

    @Override
    public Feedback findEntityById(long id) throws DAOFailedException {
        Feedback feedback = new Feedback();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_FEEDBACK_BY_ID);
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
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return feedback;
    }

    @Override
    public void updateFeedbackStatus(boolean isAccepted, long id) throws DAOFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_UPDATE_FEEDBACK_STATUS);
            if (isAccepted){
                st.setString(1,FeedbackStatus.PUBLISHED.getStatus());
            } else {
                st.setString(1,FeedbackStatus.REJECTED.getStatus());
            }
            st.setLong(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find a feedback: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Feedback> findFeedbacksByUserId(long id) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_FEEDBACKS_OF_USER);
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
            throw new DAOFailedException("Impossible to get feedbacks: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return feedbacksList;
    }

    @Override
    public List<Feedback> findUserMarks(long id) throws DAOFailedException {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MARKS_OF_USER);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setMark(rs.getInt("mark"));
                feedback.setMovie(new Movie(rs.getLong("id_movie"), rs.getString("title")));
                feedbacksList.add(feedback);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to get feedbacks: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return feedbacksList;
    }
}
