package com.movierate.movie.dao;

import com.movierate.movie.entity.Movie;

import java.util.List;

/**
 * specifies methods for working with entity "movie" that its implementations should realize
 */
public interface MovieDAOI {

    boolean save(Movie movie);
    List<Movie> findMovieByGenre (String genre, int start, int pageQuantity);
}
