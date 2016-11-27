package com.movierate.movie.dao;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.NotValidOperationException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yultos_ on 27.11.2016
 */
public class UserDAO extends AbstractDAO <User> {

    public static final Logger LOGGER = LogManager.getLogger(UserDAO.class);
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT users.login FROM users WHERE login=?";

    @Override
    public List<User> findAll() {
        return null;
//        throw new NotValidOperationException("Operation is not supported");
    }

    @Override
    public List<User> findEntityByName (String login){
        List<User> entityList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (ProxyConnection connection = pool.takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN) ){
            st.setString(1,login);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setLogin(rs.getString("login"));
                entityList.add(user);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Request failed "+e.getMessage());
        }
        return entityList;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }
}
