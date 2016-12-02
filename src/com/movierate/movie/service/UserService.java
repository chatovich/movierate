package com.movierate.movie.service;

import com.movierate.movie.entity.User;
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
    public User createUser (Map<String, String[]> parameters, String filePath){
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
        return user;
    }
}
