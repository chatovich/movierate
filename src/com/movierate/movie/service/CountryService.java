package com.movierate.movie.service;

import com.movierate.movie.dao.impl.CountryDAOImpl;
import com.movierate.movie.entity.Country;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;

import java.util.List;

/**
 * Class that encapsulates logic connected with entity "country" and represents intermediate layer between database and client
 */
public class CountryService {

    /**
     * gets all countries
     * @return list of countries
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Country> getCountries() throws ServiceException {
        CountryDAOImpl countryDAO = new CountryDAOImpl();
        List<Country> countries;
        try {
            countries = countryDAO.findAll();
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return countries;
    }
}
