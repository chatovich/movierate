package com.movierate.movie.service;

import com.movierate.movie.dao.impl.GenreDAOImpl;
import com.movierate.movie.entity.Genre;

import java.util.List;

/**
 * Class that encapsulates logic connected with entity "genre" and represents intermediate layer between database and client
 */
public class GenreService {

    public List<Genre> getGenres(){
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        return genreDAO.findAll();

    }
}
