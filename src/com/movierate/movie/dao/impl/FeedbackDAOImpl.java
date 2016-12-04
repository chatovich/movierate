package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAOI;
import com.movierate.movie.dao.FeedbackDAOI;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.User;
import com.movierate.movie.type.FeedbackStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "feedbacks"
 */
public class FeedbackDAOImpl implements FeedbackDAOI, DAOI {

    public static final Logger LOGGER = LogManager.getLogger(MarkDAOImpl.class);
    public static final String SQL_FIND_FEEDBACKS_OF_MOVIE = "SELECT id_feedback, text, from_user, likes, creating_date, status, users.login, users.photo " +
            "FROM feedbacks JOIN users ON from_user=id_user WHERE to_movie=?;";
    public static final String SQL_SAVE_FEEDBACK = "INSERT INTO feedbacks (text,from_user,to_movie, creating_date) VALUES (?,?,?,?)";


    /**
     * finds all feedbacks of the movie by movie id
     * @param id id of the movie
     * @return list containing feedbacks of the movie
     */
    @Override
    public List <Feedback> findEntityById(int id) {
        List<Feedback> feedbacksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_FEEDBACKS_OF_MOVIE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id_feedback"));
                feedback.setText(rs.getString("text"));
                feedback.setLikes(rs.getInt("likes"));
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
    public boolean save(Feedback feedback) {
        boolean isCreated = false;
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
            if (st.executeUpdate()>0){
                isCreated = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }

        return isCreated;
    }
}
