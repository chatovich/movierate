package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.GenreDAO;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.exception.DAOFailedException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "genres"
 */
public class GenreDAOImpl implements GenreDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(GenreDAOImpl.class);
    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT * FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";
    public static final String SQL_FIND_GENRE_BY_NAME = "SELECT id_genre, genre FROM genres WHERE genre=?";
    public static final String SQL_FIND_ALL_GENRES = "SELECT id_genre, genre FROM genres";
    public static final String SQL_SAVE_GENRE = "INSERT INTO genres (genre) VALUE (?)";


    @Override
    public List<Genre> findGenresByMovieName(String name) {
        List<Genre> genresList = new ArrayList<>();
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

    @Override
    public Genre findEntityByName(String name) {
        Genre genre = new Genre();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_GENRE_BY_NAME);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                genre.setId(rs.getLong("id_genre"));
                genre.setGenreName(rs.getString("genre"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return genre;
    }

    /**
     * finds all genres of the movie by movie id
     * @param id id of the movie
     * @return list containing genres of the movie
     */
    public List<Genre> findGenresByMovieId(int id) {
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
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return genresList;
    }


    @Override
    public List<Genre> findAll() {
        List<Genre> genresList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        Statement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_GENRES);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id_genre"));
                genre.setGenreName(rs.getString("genre"));
                genresList.add(genre);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return genresList;
    }

    @Override
    public void save(Genre genre) throws DAOFailedException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_SAVE_GENRE);
            st.setString(1, genre.toString());
            st.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
            throw new DAOFailedException("Problem connecting with db"+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
    }
}
