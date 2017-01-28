package com.chatovich.movie.dao;

import com.chatovich.movie.entity.Country;
import com.chatovich.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "country" that its implementations should realize
 */
public interface ICountryDAO {
    List<Country> findCountriesByMovieId(long id) throws DAOFailedException;
    Country findEntityByName (String name) throws DAOFailedException;
    List<Country> findAll() throws DAOFailedException;
}
