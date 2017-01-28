package com.chatovich.movie.service;

import com.chatovich.movie.entity.Country;
import com.chatovich.movie.exception.ServiceException;

import java.util.List;

/**
 * interface with operations with entity "country"
 */
public interface ICountryService {

    List<Country> getCountries() throws ServiceException;
}
