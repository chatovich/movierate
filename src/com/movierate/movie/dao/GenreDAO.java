package com.movierate.movie.dao;

import com.movierate.movie.entity.Genre;
import com.movierate.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "genre" that its implementations should realize
 */
public interface GenreDAO {

    List<Genre> findGenresByMovieName(String name);
    Genre findEntityByName (String name);
    List<Genre> findAll();
    void save(Genre genre) throws DAOFailedException;
}
