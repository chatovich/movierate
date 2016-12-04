package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAOI;
import com.movierate.movie.dao.UserDAOI;
import com.movierate.movie.entity.User;
import com.movierate.movie.type.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects to database and operate with table "users"
 */
public class UserDAOImpl implements UserDAOI, DAOI {

    public static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    public static final String SQL_SAVE_USER = "INSERT into users (login, password, e_mail, registr_date, role, photo) " +
            "VALUES (?,?,?,?,?,?)";


    @Override
    public List<User> findEntityByName (String login){
        List<User> entityList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (ProxyConnection connection = pool.takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN) ){
            st.setString(1,login);
            ResultSet rs = st.executeQuery();
            User user = new User();
            //change to long1!!
            user.setId(rs.getInt("id_user"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("e_mail"));
            user.setPoints(rs.getInt("points"));
            user.setPhoto(rs.getString("photo"));
            //change to double!!!
            user.setRating(rs.getInt("rating"));
            user.setBanned(rs.getInt("isBanned")!=0);
            //when in db string instead of enum!!
            user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
            entityList.add(user);


        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Request failed "+e.getMessage());
        }
        return entityList;
    }


    @Override
    public boolean save(User user) {
        boolean isCreated = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        try (ProxyConnection connection = pool.takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_SAVE_USER) ){
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getRegistrDate().toString());
            st.setString(5, user.getRole().toString().toLowerCase());
            st.setString(6, user.getPhoto());
            if (st.executeUpdate()>0){
                isCreated = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Request failed "+e.getMessage());
        }
        return isCreated;

    }

    @Override
    public List findEntityById(int id) {
        return null;
    }
}
