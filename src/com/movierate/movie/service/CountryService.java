package com.movierate.movie.service;

import com.movierate.movie.dao.impl.CountryDAOImpl;
import com.movierate.movie.entity.Country;

import java.util.List;

/**
 * Class that encapsulates logic connected with entity "country" and represents intermediate layer between database and client
 */
public class CountryService {

    public List<Country> getCountries(){
        CountryDAOImpl countryDAO = new CountryDAOImpl();
        return countryDAO.findAll();
    }
}
