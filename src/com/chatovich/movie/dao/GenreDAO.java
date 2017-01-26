package com.chatovich.movie.dao;

import com.chatovich.movie.entity.Genre;
import com.chatovich.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "genre" that its implementations should realize
 */
public interface GenreDAO {

    Genre findEntityByName (String name) throws DAOFailedException;
    List<Genre> findGenresByMovieId (long id) throws DAOFailedException;
    List<Genre> findAll() throws DAOFailedException;
}
