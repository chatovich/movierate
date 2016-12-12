package com.movierate.movie.service;

import com.movierate.movie.dao.impl.FeedbackDAOImpl;
import com.movierate.movie.dao.impl.UserDAOImpl;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.type.Role;
import com.movierate.movie.util.PasswordHash;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class that encapsulates logic connected with entity "user" and represents intermediate layer between database and client
 */
public class UserService {

    /**
     * creates a new Object "User" using tha data from inout form on registration page
     * @param parameters map with all the parameters of the input form, where key is a name of the parameter, value - data that was inserted by user
     * @param filePath absolute path to the photo uploaded by user
     */
    public boolean createUser (Map<String, String[]> parameters, String filePath){
        User user = new User();
        user.setPhoto(filePath);
        user.setRole(Role.USER);
        user.setRegistrDate(LocalDate.now());
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            switch (entry.getKey()){
                case "username": user.setLogin(entry.getValue()[0]);
                    break;
                case "password": user.setPassword(PasswordHash.getHashPassword(entry.getValue()[0]));
                    break;
                case "email": user.setEmail(entry.getValue()[0]);
                    break;
            }
        }
        UserDAOImpl userDAO = new UserDAOImpl();
        return userDAO.save(user);
    }

    public boolean loginAvailable (String login) throws DAOFailedException {
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        User user = userDAOImpl.findUserByLogin(login);
        return user.getLogin()==null;
    }

    public User getUser(Map<String, String[]> parameters) throws DAOFailedException {

        String login = parameters.get("login")[0];
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        User user = userDAOImpl.findEntityByName(login);
        user.setUserFeedbacks(feedbackDAO.findFeedbacksByUserId(user.getId()));
        return user;
    }

    public User getLoginInfo (Map<String, String[]> parameters) throws DAOFailedException {

        String login = parameters.get("login")[0];
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        return userDAOImpl.findUserByLogin(login);
    }

    public void updateUser (Map<String, String[]> parameters, String path) throws DAOFailedException {

        String email = "";
        String password = "";
        String login = "";
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            switch (entry.getKey()){
                case "email": email = entry.getValue()[0];
                    break;
                case "login": login = entry.getValue()[0];
                case "password": if (password.isEmpty()){
                    password = entry.getValue()[0];}
                    break;
                case "new_password": if (!entry.getValue()[0].isEmpty()){
                    password = entry.getValue()[0];}
                    break;
            }
        }
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.updateUser(login, email, PasswordHash.getHashPassword(password), path);

    }
}
