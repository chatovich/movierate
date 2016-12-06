package com.movierate.movie.dao;

import com.movierate.movie.entity.User;

import java.util.List;

/**
 * specifies methods for working with entity "user" that its implementations should realize
 */
public interface UserDAO {

    List<User> findEntityByName(String name);
    boolean save(User user);
    boolean checkLoginAvailable(String login);
}
