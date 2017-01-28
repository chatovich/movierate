package com.chatovich.movie.service;

import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * interface with operations to work with entity "movie"
 */
public interface IMovieService {

    Movie findMovieById(long id) throws ServiceException;
    void createMovie(Map<String,String[]> parameters, String path) throws ServiceException;
    boolean movieExists (String title) throws ServiceException;
    List<Movie> getAllMovies () throws ServiceException;
    List<Movie> filteredMovieSearch (Map <String, String[]> parameters, int start, int moviesPerPage) throws ServiceException;
    int getMovieQuantity ();
    void deleteMovie(long id_movie) throws ServiceException;
    List<Movie> findTopMovies() throws ServiceException;

}
