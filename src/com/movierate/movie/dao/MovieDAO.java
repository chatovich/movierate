package com.movierate.movie.dao;

import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.Movie;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.RollbackFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "movie" that its implementations should realize
 */
public interface MovieDAO {

    void save(Movie movie) throws DAOFailedException, RollbackFailedException;
//    List<Movie> findMovieByGenre (String genre, int start, int pageQuantity);
//    long getIdByTitle (String title) throws DAOFailedException;
    boolean checkMovieExists (String title) throws DAOFailedException;
    List<Movie> findAll() throws DAOFailedException;
    Movie findEntityById(long id) throws DAOFailedException;
//    List<Movie> findMoviesByDynamicId(List<Feedback> feedbacks) throws DAOFailedException;
    List<Movie> findFilteredMovies (String query, int start, int moviesPerPage) throws DAOFailedException;
    void deleteMovie(long id) throws DAOFailedException, RollbackFailedException;
    void updateMovieRating(double rating, long id_movie) throws DAOFailedException;
    List<Movie> findTopMovies() throws DAOFailedException;
}
