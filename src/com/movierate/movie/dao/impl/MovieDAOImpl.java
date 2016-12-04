package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAOI;
import com.movierate.movie.dao.MovieDAOI;
import com.movierate.movie.entity.Entity;
import com.movierate.movie.entity.Movie;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "movies"
 */
public class MovieDAOImpl implements MovieDAOI, DAOI {

    public static final Logger LOGGER = LogManager.getLogger(MovieDAOImpl.class);
    public static final String SQL_FIND_MOVIES_BY_GENRE = "SELECT SQL_CALC_FOUND_ROWS * FROM movies WHERE id_movie IN (SELECT id_movie FROM movies_genres JOIN genres ON movies_genres.id_genre=genres.id_genre WHERE genre=?) LIMIT ?,?;";
    public static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT genre FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";
    public static final String SQL_FIND_MOVIE_BY_ID = "SELECT * FROM movies WHERE id_movie=?";

    private int movieQuantity;

    @Override
    public List<Movie> findEntityById(int id) {
        List<Movie> moviesList = new ArrayList<>();
        Movie movie = new Movie();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MOVIE_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
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
        return moviesList;

    }


    @Override
    public boolean save(Movie movie) {
        return false;
    }

    /**
     * finds all movies by given genre
     * @param genre user makes request to see movies of this genre
     */
    public List<Movie> findMovieByGenre (String genre, int start, int pageQuantity){

        List<Movie> moviesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_MOVIES_BY_GENRE);
            st.setString(1,genre);
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
            if (rs.next()){
                movieQuantity = rs.getInt(1);
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
        return moviesList;

    }

    public int getPageQuantity (){
        return this.movieQuantity;
    }
}
