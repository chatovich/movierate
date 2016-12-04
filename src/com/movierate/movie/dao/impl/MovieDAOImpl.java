package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAOI;
import com.movierate.movie.dao.MovieDAOI;
import com.movierate.movie.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "movies"
 */
public class MovieDAOImpl implements MovieDAOI, DAOI {

    public static final Logger LOGGER = LogManager.getLogger(MovieDAOImpl.class);
    public static final String SQL_INSERT_MOVIES_GENRES = "INSERT INTO movies_has_genres (movies_id_movie, genres_id_genre) VALUES (?,?)";
    public static final String SQL_INSERT_MOVIES_COUNTRIES = "INSERT INTO movies_countries (id_movie, id_country) VALUES (?,?)";
    public static final String SQL_INSERT_MOVIES_PARTICIPANTS = "INSERT INTO movies_participants (id_movie, id_participant) VALUES (?,?)";
    public static final String SQL_FIND_MOVIES_BY_GENRE = "SELECT SQL_CALC_FOUND_ROWS * FROM movies WHERE id_movie IN (SELECT id_movie FROM movies_genres JOIN genres ON movies_genres.id_genre=genres.id_genre WHERE genre=?) LIMIT ?,?;";
    public static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT genre FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";
    public static final String SQL_FIND_MOVIE_BY_ID = "SELECT id_movie, title, rating,year,plot,poster,trailer,duration,points FROM movies WHERE id_movie=?";
    public static final String SQL_GET_ID_BY_TITLE = "SELECT id_movie FROM movies WHERE title=?";
    public static final String SQL_SAVE_MOVIE = "INSERT INTO movies (title, year, plot, poster, duration, adding_date) VALUES (?,?,?,?,?,?)";

    private int movieQuantity;

    @Override
    public List<Movie> findEntityById(int id) {
        List<Movie> moviesList = new ArrayList<>();
        Movie movie = new Movie();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MOVIE_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                movie.setId(rs.getInt("id_movie"));
                movie.setTitle(rs.getString("title"));
                movie.setRating(rs.getDouble("rating"));
                movie.setYear(rs.getInt("year"));
                movie.setPlot(rs.getString("plot"));
                movie.setPoster(rs.getString("poster"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setDuration(rs.getInt("duration"));
                movie.setPoints(rs.getInt("points"));
                moviesList.add(movie);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return moviesList;

    }

    /**
     * saves an object "movie" in the database, making also new lines in tables "movies.genres", "movies.countries",
     * "movies.participants" through one transaction
     * @param movie object "movie" that should be placed into database
     */
    @Override
    public void save(Movie movie) {
        boolean isCreated = false;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement stMovie = null;
        PreparedStatement stGenre = null;
        PreparedStatement stCountry = null;
        PreparedStatement stParticipant = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            stMovie = connection.prepareStatement(SQL_SAVE_MOVIE,  Statement.RETURN_GENERATED_KEYS);
            stMovie.setString(1, movie.getTitle());
            stMovie.setInt(2, movie.getYear());
            stMovie.setString(3, movie.getPlot());
            stMovie.setString(4, movie.getPoster());
            stMovie.setInt(5, movie.getDuration());
            stMovie.setDate(6, Date.valueOf(movie.getAdding_date()));
            stMovie.executeUpdate();
            ResultSet rs = stMovie.getGeneratedKeys();
            if (rs.next()){
                movie.setId(rs.getLong(1));
            }

//            long id_movie = getIdByTitle(movie.getTitle());
//            long id_movie = 11;
            stGenre = connection.prepareStatement(SQL_INSERT_MOVIES_GENRES);
            for (Genre genre : movie.getMovieGenres()) {
                stGenre.setLong(1, movie.getId());
                stGenre.setLong(2, genre.getId());
                stGenre.executeUpdate();
            }
            stCountry = connection.prepareStatement(SQL_INSERT_MOVIES_COUNTRIES);
            for (Country country : movie.getMovieCountries()) {
                stCountry.setLong(1, movie.getId());
                stCountry.setLong(2, country.getId());
                stCountry.executeUpdate();
            }
            stParticipant = connection.prepareStatement(SQL_INSERT_MOVIES_PARTICIPANTS);
            for (Participant participant: movie.getMovieParticipants()) {
                stParticipant.setLong(1,movie.getId());
                stParticipant.setLong(2,participant.getId());
                stParticipant.executeUpdate();
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.log(Level.ERROR, "Problem rolling back: " + e.getMessage());
            }
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());

        } finally {
            close(stMovie);
            close(stGenre);
            close(stParticipant);
            close(stCountry);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * finds all movies by given genre
     *
     * @param genre user makes request to see movies of this genre
     */
    public List<Movie> findMovieByGenre(String genre, int start, int pageQuantity) {

        List<Movie> moviesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MOVIES_BY_GENRE);
            st.setString(1, genre);
            st.setInt(2, start);
            st.setInt(3, pageQuantity);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id_movie"));
                movie.setTitle(rs.getString("title"));
                movie.setRating(rs.getDouble("rating"));
                movie.setYear(rs.getInt("year"));
                movie.setPlot(rs.getString("plot"));
                movie.setPoster(rs.getString("poster"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setDuration(rs.getInt("duration"));
                movie.setPoints(rs.getInt("points"));
                //movie.setMovieGenres();
                moviesList.add(movie);
            }
            rs.close();

            rs = st.executeQuery(SQL_FOUND_ROWS);
            if (rs.next()) {
                movieQuantity = rs.getInt(1);
            }


        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return moviesList;

    }

    public int getPageQuantity() {
        return this.movieQuantity;
    }

    @Override
    public int getIdByTitle(String title) {
        int id_movie = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_GET_ID_BY_TITLE);
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id_movie = rs.getInt("id_movie");
            } else{
                // throw new ...
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());
        }
        return id_movie;
    }
}
