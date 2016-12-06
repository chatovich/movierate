package com.movierate.movie.service;

import com.movierate.movie.dao.impl.GenreDAOImpl;
import com.movierate.movie.entity.Genre;
import com.movierate.movie.exception.DAOFailedException;

import java.util.List;

/**
 * Class that encapsulates logic connected with entity "genre" and represents intermediate layer between database and client
 */
public class GenreService {

    public List<Genre> getGenres(){
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        return genreDAO.findAll();
    }

    public void createGenre (String genreName) throws DAOFailedException {
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        genreDAO.save(genre);
    }
}
