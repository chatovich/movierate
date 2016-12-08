package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.MovieDAO;
import com.movierate.movie.entity.*;
import com.movierate.movie.exception.DAOFailedException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "movies"
 */
public class MovieDAOImpl implements MovieDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(MovieDAOImpl.class);
    public static final String SQL_FIND_TITLE = "SELECT title FROM movies WHERE title=?";
    public static final String SQL_FIND_ALL_MOVIES = "SELECT id_movie, title FROM movies";
    public static final String SQL_INSERT_MOVIES_GENRES = "INSERT INTO movies_genres (id_movie, id_genre) VALUES (?,?)";
    public static final String SQL_INSERT_MOVIES_COUNTRIES = "INSERT INTO movies_countries (id_movie, id_country) VALUES (?,?)";
    public static final String SQL_INSERT_MOVIES_PARTICIPANTS = "INSERT INTO movies_participants (id_movie, id_participant) VALUES (?,?)";
    public static final String SQL_FIND_MOVIES_BY_GENRE = "SELECT SQL_CALC_FOUND_ROWS * FROM movies WHERE id_movie IN (SELECT id_movie FROM movies_genres JOIN genres ON movies_genres.id_genre=genres.id_genre WHERE genre=?) LIMIT ?,?;";
    public static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT genre FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";
    public static final String SQL_FIND_MOVIE_BY_ID = "SELECT id_movie, title, rating,year,plot,poster,trailer,duration,points, adding_date FROM movies WHERE id_movie=?";
    public static final String SQL_GET_ID_BY_TITLE = "SELECT id_movie FROM movies WHERE title=?";
    public static final String SQL_SAVE_MOVIE = "INSERT INTO movies (title, year, plot, poster, duration, adding_date) VALUES (?,?,?,?,?,?)";
    public static final String SQL_UPDATE_MOVIE = "UPDATE movies SET title=?,year=?,plot=?,poster=?,duration=?,adding_date=? WHERE id_movie=?";
    public static final String SQL_DELETE_MOVIES_GENRES = "DELETE FROM movies_genres WHERE id_movie=?";
    public static final String SQL_DELETE_MOVIES_COUNTRIES = "DELETE FROM movies_countries WHERE id_movie=?";
    public static final String SQL_DELETE_MOVIES_PARTICIPANTS = "DELETE FROM movies_participants WHERE id_movie=?";

    private int movieQuantity;

    /**
     *
     * @param id id of the movie which info is needed from db
     * @return movie object with given id or null if there is no such id_movie in the db
     */
    @Override
    public Movie findEntityById(long id) throws DAOFailedException {
        Movie movie = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MOVIE_BY_ID);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getLong("id_movie"));
                movie.setTitle(rs.getString("title"));
                movie.setRating(rs.getDouble("rating"));
                movie.setYear(rs.getInt("year"));
                movie.setPlot(rs.getString("plot"));
                movie.setPoster(rs.getString("poster"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setDuration(rs.getInt("duration"));
                movie.setPoints(rs.getInt("points"));
                movie.setAdding_date(LocalDate.parse(rs.getString("adding_date")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());
            throw new DAOFailedException("Problem finding movie in db: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return movie;

    }

    /**
     * saves an object "movie" in the database, making also new lines in tables "movies.genres", "movies.countries",
     * "movies.participants" through one transaction
     * @param movie object "movie" that should be placed into database
     */
    @Override
    public void save(Movie movie) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = connectionPool.takeConnection();
        PreparedStatement stMovie = null;
        PreparedStatement stGenre = null;
        PreparedStatement stCountry = null;
        PreparedStatement stParticipant = null;
        PreparedStatement stGenreDel = null;
        PreparedStatement stCountryDel = null;
        PreparedStatement stParticipantDel = null;

        try {
            try {
                connection.setAutoCommit(false);
                if (movie.getId()==0){
                    stMovie = connection.prepareStatement(SQL_SAVE_MOVIE, Statement.RETURN_GENERATED_KEYS);
                } else {
                    stGenreDel = connection.prepareStatement(SQL_DELETE_MOVIES_GENRES);
                    deleteElementsBeforeUpdate(stGenreDel, movie.getId());
                    stCountryDel = connection.prepareStatement(SQL_DELETE_MOVIES_COUNTRIES);
                    deleteElementsBeforeUpdate(stCountryDel, movie.getId());
                    stParticipantDel = connection.prepareStatement(SQL_DELETE_MOVIES_PARTICIPANTS);
                    deleteElementsBeforeUpdate(stParticipantDel, movie.getId());
                    stMovie = connection.prepareStatement(SQL_UPDATE_MOVIE);
                    stMovie.setLong(7,movie.getId());
                }
                stMovie.setString(1, movie.getTitle());
                stMovie.setInt(2, movie.getYear());
                stMovie.setString(3, movie.getPlot());
                stMovie.setString(4, movie.getPoster());
                stMovie.setInt(5, movie.getDuration());
                stMovie.setDate(6, Date.valueOf(movie.getAdding_date()));
                stMovie.executeUpdate();
                if (movie.getId()==0) {
                    ResultSet rs = stMovie.getGeneratedKeys();
                    if (rs.next()) {
                        movie.setId(rs.getLong(1));
                    }
                }
//            } catch (SQLException e) {
//                throw e;
//                try {
//                    connection.rollback();
//                } catch (SQLException e1) {
//                    LOGGER.log(Level.ERROR, "Problem rolling back: " + e.getMessage());
//                }
//                LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());

            } finally {
                close(stMovie);
            }

            try {
                stGenre = connection.prepareStatement(SQL_INSERT_MOVIES_GENRES);
                for (Genre genre : movie.getMovieGenres()) {
                    stGenre.setLong(1, movie.getId());
                    stGenre.setLong(2, genre.getId());
                    stGenre.executeUpdate();
                }
            } finally {
                close(stGenre);
            }

            try {
                stCountry = connection.prepareStatement(SQL_INSERT_MOVIES_COUNTRIES);
                for (Country country : movie.getMovieCountries()) {
                    stCountry.setLong(1, movie.getId());
                    stCountry.setLong(2, country.getId());
                    stCountry.executeUpdate();
                }
            } finally {
                close(stCountry);
            }

            try {
                stParticipant = connection.prepareStatement(SQL_INSERT_MOVIES_PARTICIPANTS);
                for (Participant participant: movie.getMovieParticipants()) {
                    stParticipant.setLong(1,movie.getId());
                    stParticipant.setLong(2,participant.getId());
                    stParticipant.executeUpdate();
                }
            } finally {
                close(stParticipant);
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

    @Override
    public boolean checkMovieExists(String title) {

        boolean movieExists = false;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_TITLE);
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                movieExists = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return movieExists;
    }

    @Override
    public List<Movie> findAll() throws DAOFailedException {

        List <Movie> movies = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        Statement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_MOVIES);
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong("id_movie"));
                movie.setTitle(rs.getString("title"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db " + e.getMessage());
            throw new DAOFailedException("Finding all movies failed: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return movies;
    }

    private void deleteElementsBeforeUpdate (PreparedStatement st, long id) throws SQLException {

        try {
            st.setLong(1,id);
            st.executeUpdate();
        } finally {
            close(st);
        }
    }
}
