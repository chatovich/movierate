package com.chatovich.movie.service;

import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by Yultos_ on 28.01.2017.
 */
public interface IUserService {

    void createUser (Map<String, String[]> parameters, String filePath) throws ServiceException;
    boolean loginAvailable (String login) throws ServiceException;
    User getUser(String login) throws ServiceException;
    User getLoginInfo (Map<String, String[]> parameters) throws ServiceException;
    void updateUser (Map<String, String[]> parameters, String path) throws ServiceException;
    double defineUserRating(String id) throws ServiceException;
    User updateUserFeedbacks (User user) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
    void changeUserStatus (String login, boolean toBan) throws ServiceException;
    void controlBan();

}
