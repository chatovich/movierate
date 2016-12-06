package com.movierate.movie.dao;

import com.movierate.movie.entity.Country;

import java.util.List;

/**
 * specifies methods for working with entity "country" that its implementations should realize
 */
public interface CountryDAO {
    List<Country> findCountriesByMovieId(int id);
    Country findEntityByName (String name);
    List<Country> findAll();
}
