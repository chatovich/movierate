package com.movierate.movie.dao;

import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "user" that its implementations should realize
 */
public interface UserDAO {

    User findEntityByName(String name) throws DAOFailedException;
    boolean save(User user);
    User findUserByLogin(String login) throws DAOFailedException;
    void updateUser (String login, String email, String password, String path) throws DAOFailedException;
}
