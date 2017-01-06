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

    private static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT * FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";
    private static final String SQL_FIND_GENRE_BY_NAME = "SELECT id_genre, genre FROM genres WHERE genre=?";
    private static final String SQL_FIND_ALL_GENRES = "SELECT id_genre, genre FROM genres";

    /**
     * finds genre by its name
     * @param name genre name
     * @return genre
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public Genre findEntityByName(String name) throws DAOFailedException {
        Genre genre = new Genre();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try(
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_GENRE_BY_NAME)){
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                genre.setId(rs.getLong("id_genre"));
                genre.setGenreName(rs.getString("genre"));
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find genre: "+e.getMessage());
        }
        return genre;
    }

    /**
     * finds all genres of the movie by movie id
     * @param id id of the movie
     * @return list containing genres of the movie
     */
    @Override
    public List<Genre> findGenresByMovieId(long id) throws DAOFailedException {
        List<Genre> genresList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_GENRES_OF_MOVIE)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id_genre"));
                genre.setGenreName(rs.getString("genre"));
                genresList.add(genre);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find movie's genres: "+e.getMessage());
        }
        return genresList;
    }


    /**
     * finds all genres
     * @return list of all genres
     */
    @Override
    public List<Genre> findAll() throws DAOFailedException {
        List<Genre> genresList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_GENRES);
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id_genre"));
                genre.setGenreName(rs.getString("genre"));
                genresList.add(genre);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find all genres: "+e.getMessage());
        }
        return genresList;
    }
}
