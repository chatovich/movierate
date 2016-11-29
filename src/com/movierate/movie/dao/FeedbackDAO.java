package com.movierate.movie.dao;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.entity.Entity;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Mark;
import com.movierate.movie.entity.User;
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
public class FeedbackDAO extends AbstractDAO{

    public static final Logger LOGGER = LogManager.getLogger(MarkDAO.class);
    public static final String SQL_FIND_FEEDBACKS_OF_MOVIE = "SELECT id_feedback, text, from_user, likes, creating_date, users.login, users.photo " +
            "FROM feedbacks JOIN users ON from_user=id_user WHERE to_movie=?;";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findEntityByName(String name) {
        return null;
    }

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
                feedback.setCreatingDate(LocalDate.parse(rs.getString("creating_date")));
                feedback.setUser(new User(rs.getInt("from_user"), rs.getString("users.login"), rs.getString("users.photo")));
                feedbacksList.add(feedback);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            if (st!=null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
                }
            }
            connectionPool.releaseConnection(connection);
        }
        return feedbacksList;
    }

    @Override
    public boolean create(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }
}
