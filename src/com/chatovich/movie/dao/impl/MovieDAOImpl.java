package com.chatovich.movie.dao.impl;

import com.chatovich.movie.connection.ConnectionPool;
import com.chatovich.movie.connection.ProxyConnection;
import com.chatovich.movie.dao.DAO;
import com.chatovich.movie.dao.IMovieDAO;
import com.chatovich.movie.entity.Country;
import com.chatovich.movie.entity.Genre;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.exception.RollbackFailedException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "movies" and related to it (movies_genres, movies_countries, movies_participants)
 */
public class MovieDAOImpl implements IMovieDAO, DAO {

    private static final String SQL_FIND_TITLE = "SELECT title FROM movies WHERE title=?";
    private static final String SQL_FIND_ALL_MOVIES = "SELECT id_movie, title FROM movies ORDER BY title";
    private static final String SQL_FIND_MOVIE_BY_TITLE = "SELECT SQL_CALC_FOUND_ROWS id_movie, poster FROM movies WHERE title=? LIMIT ?,?";
    private static final String SQL_FIND_TOP_MOVIES = "SELECT id_movie, title, rating FROM movies ORDER BY rating DESC LIMIT 10";
    private static final String SQL_INSERT_MOVIES_GENRES = "INSERT INTO movies_genres (id_movie, id_genre) VALUES (?,?)";
    private static final String SQL_INSERT_MOVIES_COUNTRIES = "INSERT INTO movies_countries (id_movie, id_country) VALUES (?,?)";
    private static final String SQL_INSERT_MOVIES_PARTICIPANTS = "INSERT INTO movies_participants (id_movie, id_participant) VALUES (?,?)";
    private static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
    private static final String SQL_UPDATE_MOVIE_RATING = "UPDATE movies SET rating=? WHERE id_movie=?";
    private static final String SQL_FIND_MOVIE_BY_ID = "SELECT id_movie, title, rating,year,plot,poster,trailer,duration,points, adding_date FROM movies WHERE id_movie=?";
    private static final String SQL_SAVE_MOVIE = "INSERT INTO movies (title, year, plot, poster, duration, adding_date) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_MOVIE = "UPDATE movies SET title=?,year=?,plot=?,poster=?,duration=?,adding_date=? WHERE id_movie=?";
    private static final String SQL_DELETE_MOVIES_GENRES = "DELETE FROM movies_genres WHERE id_movie=?";
    private static final String SQL_DELETE_MOVIES_COUNTRIES = "DELETE FROM movies_countries WHERE id_movie=?";
    private static final String SQL_DELETE_MOVIES_PARTICIPANTS = "DELETE FROM movies_participants WHERE id_movie=?";
    private static final String SQL_DELETE_FEEDBACKS = "DELETE FROM feedbacks WHERE to_movie=?";
    private static final String SQL_DELETE_MOVIE = "DELETE FROM movies WHERE id_movie=?";
    private static final String SQL_DELETE_LIKES = "DELETE FROM likes WHERE feedback IN (SELECT id_feedback from feedbacks WHERE to_movie = ?)";


    private static int movieQuantity;

    /**
     * finds movie by id
     * @param id id of the movie which info is needed from db
     * @return movie object with given id or null if there is no such id_movie in the db
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public Movie findEntityById(long id) throws DAOFailedException {
        Movie movie = new Movie();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_MOVIE_BY_ID)){
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
                movie.setAddingDate(LocalDate.parse(rs.getString("adding_date")));
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Problem finding movie in db: "+e.getMessage());
        }
        return movie;

    }

    /**
     * saves an object "movie" in the database, making also new lines in tables "movies.genres", "movies.countries",
     * "movies.participants" through one transaction
     * @param movie object "movie" that should be placed into database
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public void save(Movie movie) throws DAOFailedException, RollbackFailedException {
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
                stMovie.setDate(6, Date.valueOf(movie.getAddingDate()));
                stMovie.executeUpdate();
                if (movie.getId()==0) {
                    ResultSet rs = stMovie.getGeneratedKeys();
                    if (rs.next()) {
                        movie.setId(rs.getLong(1));
                    }
                }
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
                throw new RollbackFailedException("Connection rollback failed while saving a movie: "+e.getMessage());
            }
            throw new DAOFailedException("Impossible to save movie into the db: "+e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * deletes movie from the db
     * @param id movie id
     * @throws DAOFailedException if SQLException is thrown
     * @throws RollbackFailedException if SQLException is thrown during rollback try
     */
    @Override
    public void deleteMovie(long id) throws DAOFailedException, RollbackFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement stCountryDel = null;
        PreparedStatement stGenreDel = null;
        PreparedStatement stParticipantDel = null;
        PreparedStatement stDeleteLikes = null;
        PreparedStatement stDeleteFeedbacks = null;
        PreparedStatement stDeleteMovie = null;
        try{
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            stCountryDel = connection.prepareStatement(SQL_DELETE_MOVIES_COUNTRIES);
            deleteElementsBeforeUpdate(stCountryDel, id);
            stGenreDel = connection.prepareStatement(SQL_DELETE_MOVIES_GENRES);
            deleteElementsBeforeUpdate(stGenreDel, id);
            stParticipantDel = connection.prepareStatement(SQL_DELETE_MOVIES_PARTICIPANTS);
            deleteElementsBeforeUpdate(stParticipantDel, id);
            stDeleteLikes = connection.prepareStatement(SQL_DELETE_LIKES);
            deleteElementsBeforeUpdate(stDeleteLikes, id);
            stDeleteFeedbacks = connection.prepareStatement(SQL_DELETE_FEEDBACKS);
            deleteElementsBeforeUpdate(stDeleteFeedbacks,id);
            stDeleteMovie = connection.prepareStatement(SQL_DELETE_MOVIE);
            deleteElementsBeforeUpdate(stDeleteMovie,id);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RollbackFailedException("Connection rollback failed while deleting a movie: "+e.getMessage());
            }
            throw new DAOFailedException("Impossible to delete movie: "+e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * checks whether the db already contains a movie with this title
     * @param title movie title
     * @return true if such movie exists, otherwise false
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public boolean checkMovieExists(String title) throws DAOFailedException {

        boolean movieExists = false;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_TITLE)){
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                movieExists = true;
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to check whether movie exists: "+e.getMessage());
        }
        return movieExists;
    }

    /**
     * finds all movies from the db
     * @return list of all movies
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Movie> findAll() throws DAOFailedException {

        List <Movie> movies = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = connectionPool.takeConnection();
            Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_MOVIES);
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong("id_movie"));
                movie.setTitle(rs.getString("title"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Finding all movies failed: "+e.getMessage());
        }
        return movies;
    }

    /**
     * finds movies according to user request
     * @param query query that was built using parameters entered by user
     * @param start index movie number from which movies are gonna display on this page
     * @param moviesPerPage quantity of movies displayed on each page
     * @return list of movies to be displayed on one page
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Movie> findFilteredMovies(String query, int start, int moviesPerPage) throws DAOFailedException {
        List<Movie> moviesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(query)){
            st.setInt(1, start);
            st.setInt(2, moviesPerPage);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id_movie"));
                movie.setTitle(rs.getString("title"));
                movie.setPoster(rs.getString("poster"));
                movie.setAddingDate(LocalDate.parse(rs.getString("adding_date")));
                movie.setYear(rs.getInt("year"));
                moviesList.add(movie);
            }
            rs.close();

            rs = st.executeQuery(SQL_FOUND_ROWS);
            if (rs.next()) {
                movieQuantity = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to get filtered movies: "+e.getMessage());
        }
        return moviesList;
    }

    /**
     * gets quantity of movies got by sql request
     * @return movie quantity
     */
    public int getMovieQuantity() {
        return movieQuantity;
    }


    /**
     * updates movie rating when user leaves feedback and mark to movie
     * @param rating new movie rating
     * @param idMovie movie id
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public void updateMovieRating(double rating, long idMovie) throws DAOFailedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_UPDATE_MOVIE_RATING)){
            st.setDouble(1, rating);
            st.setLong(2, idMovie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Updating movie rating failed: "+e.getMessage());
        }
    }

    /**
     * finds 10 top movies by rating for loading main page
     * @return list of 10 top movies
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Movie> findTopMovies() throws DAOFailedException {
        List <Movie> topMovies = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
                ProxyConnection connection = connectionPool.takeConnection();
            Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(SQL_FIND_TOP_MOVIES);
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong("id_movie"));
                movie.setTitle(rs.getString("title"));
                movie.setRating(rs.getDouble("rating"));
                topMovies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Finding top movies failed: "+e.getMessage());
        }
        return topMovies;
    }

    /**
     * finds all movies with specified title
     * @param title of the movie
     * @return list with movies
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Movie> findMovieByTitle(String title, int start, int moviesPerPage) throws DAOFailedException {
        List<Movie> movies = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (
                ProxyConnection connection = connectionPool.takeConnection();
                PreparedStatement st = connection.prepareStatement(SQL_FIND_MOVIE_BY_TITLE)){
            st.setString(1, title);
            st.setInt(2, start);
            st.setInt(3, moviesPerPage);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id_movie"));
                movie.setPoster(rs.getString("poster"));
                movies.add(movie);
            }
            rs.close();

            rs = st.executeQuery(SQL_FOUND_ROWS);
            if (rs.next()) {
                movieQuantity = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find movie by its title: "+e.getMessage());
        }
        return movies;
    }

    /**
     * executes updates of deleting movie elements
     * @param st prepared statement
     * @param id id of the row to delete
     * @throws SQLException if SQLException is thrown
     */
    private void deleteElementsBeforeUpdate (PreparedStatement st, long id) throws SQLException {

        try {
            st.setLong(1,id);
            st.executeUpdate();
        } finally {
            close(st);
        }
    }
}
