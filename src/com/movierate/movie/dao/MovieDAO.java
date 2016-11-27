package com.movierate.movie.dao;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.entity.Entity;
import com.movierate.movie.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "movies"
 */
public class MovieDAO extends AbstractDAO {

    public static final String SQL_FIND_MOVIES_BY_GENRE = "SELECT * FROM movies WHERE id_movie IN (SELECT id_movie " +
            "FROM movies_genres JOIN genres ON movies_genres.id_genre=genres.id_genre WHERE genre=?)";
    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT genre FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findEntityByName(String name) {
        return null;
    }

    @Override
    public List <Entity> findEntityById(String id) {
        return  null;
    }

    @Override
    public boolean create(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }


    /**
     * finds all movies by given genre
     * @param genre user makes request to see movies of this genre
     */
    public List<Movie> findMovieByGenre (String genre){

        List<Movie> moviesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (ProxyConnection connection = connectionPool.takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_FIND_MOVIES_BY_GENRE)){
            st.setString(1,genre);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moviesList;

    }
}
