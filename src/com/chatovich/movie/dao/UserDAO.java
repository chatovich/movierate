package com.chatovich.movie.dao;

import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "user" that its implementations should realize
 */
public interface UserDAO {

    User findEntityByName(String name) throws DAOFailedException;
    void save(User user) throws DAOFailedException;
    User findLoginInfo(String login) throws DAOFailedException;
    void updateUser (String login, String email, String password, String path) throws DAOFailedException;
    void changeUserStatus (String login, boolean toBan) throws DAOFailedException;
    List<User> findAllUsers() throws DAOFailedException;
    List<User> findBannedUsers() throws DAOFailedException;
}
