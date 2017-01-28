package com.chatovich.movie.service.impl;

import com.chatovich.movie.dao.DAOFactory;
import com.chatovich.movie.dao.ICountryDAO;
import com.chatovich.movie.dao.impl.CountryDAOImpl;
import com.chatovich.movie.entity.Country;
import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.ICountryService;

import java.util.List;

/**
 * Class that encapsulates logic connected with entity "country" and represents intermediate layer between database and client
 */
public class CountryServiceImpl implements ICountryService {

    /**
     * gets all countries
     * @return list of countries
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Country> getCountries() throws ServiceException {
        ICountryDAO countryDAO = DAOFactory.getInstance().getCountryDAO();
        List<Country> countries;

        try {
            countries = countryDAO.findAll();
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return countries;
    }
}
