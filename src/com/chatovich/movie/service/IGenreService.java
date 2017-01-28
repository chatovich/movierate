package com.chatovich.movie.service;

import com.chatovich.movie.entity.Genre;
import com.chatovich.movie.exception.ServiceException;

import java.util.List;

/**
 * interface with operations to work with entity "genre"
 */
public interface IGenreService {

    List<Genre> getGenres() throws ServiceException;
}
