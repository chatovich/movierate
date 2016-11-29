package com.movierate.movie.dao;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.entity.Entity;
import com.movierate.movie.entity.Genre;
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
public class GenreDAO extends AbstractDAO {

    public static final Logger LOGGER = LogManager.getLogger(GenreDAO.class);
    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT * FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findEntityByName(String name) {
        List<Entity> genresList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (ProxyConnection connection = connectionPool.takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_FIND_GENRES_OF_MOVIE)){
            st.setString(1,name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {


            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+ e.getMessage());
        }
        return genresList;
    }

    /**
     * finds all genres of the movie by movie id
     * @param id id of the movie
     * @return list containing genres of the movie
     */
    @Override
    public List<Genre> findEntityById(int id) {
        List<Genre> genresList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_GENRES_OF_MOVIE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id_genre"));
                genre.setGenreName(rs.getString("genre"));
                genresList.add(genre);
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
        return genresList;
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
