package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.MarkDAO;
import com.movierate.movie.entity.Mark;
import com.movierate.movie.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "genres"
 */
public class MarkDAOImpl implements MarkDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(MarkDAOImpl.class);
    public static final String SQL_FIND_MARKS_OF_MOVIE = "SELECT id_mark, to_movie, mark, from_user, users.login, users.photo " +
            "FROM marks JOIN users ON from_user=id_user WHERE to_movie=?;";


    /**
     * finds all marks of the movie by movie id
     * @param id id of the movie
     * @return list containing marks of the movie
     */

    @Override
    public List<Mark> findMarksByMovieId(int id) {
        List<Mark> marksList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MARKS_OF_MOVIE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setId(rs.getInt("id_mark"));
                mark.setMark(rs.getInt("mark"));
                mark.setUser(new User(rs.getInt("from_user"), rs.getString("users.login"), rs.getString("users.photo")));
                marksList.add(mark);
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
        return marksList;
    }

}
