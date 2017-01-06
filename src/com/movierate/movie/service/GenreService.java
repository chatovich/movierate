package com.movierate.movie.service;

import com.movierate.movie.dao.impl.GenreDAOImpl;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that encapsulates logic connected with entity "genre" and represents intermediate layer between database and client
 */
public class GenreService {

    /**
     * gets all genres
     * @return list of genres
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Genre> getGenres() throws ServiceException {
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        List<Genre> genres;
        try {
            genres = genreDAO.findAll();
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return genres;
    }
}
