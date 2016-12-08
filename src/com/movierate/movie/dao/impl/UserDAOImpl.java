package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.UserDAO;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.type.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects to database and operate with table "users"
 */
public class UserDAOImpl implements UserDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT id_user,login,password,e_mail,points,photo,rating,isBanned,role FROM users WHERE login=?";
    public static final String SQL_SAVE_USER = "INSERT into users (login, password, e_mail, registr_date, role, photo) " +
            "VALUES (?,?,?,?,?,?)";
    public static final String SQL_FIND_LOGIN_INFO = "SELECT id_user, login, password FROM users WHERE login=?";


    @Override
    public List<User> findEntityByName (String login){
        List<User> entityList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = pool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            st.setString(1,login);
            ResultSet rs = st.executeQuery();
            //change to long1!!
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("e_mail"));
                user.setPoints(rs.getInt("points"));
                user.setPhoto(rs.getString("photo"));
                //change to double!!!
                user.setRating(rs.getInt("rating"));
                user.setBanned(rs.getInt("isBanned") != 0);
                //when in db string instead of enum!!
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                entityList.add(user);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Request failed "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
        return entityList;
    }

    /**
     *
     * @param login user login that was entered during registration
     * @return true if there is no such login in database and false if user with such login already exists
     */
    @Override
    public User findUserByLogin(String login) throws DAOFailedException {

        User user = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = pool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_LOGIN_INFO);
            st.setString(1,login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find user in db: "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        boolean isCreated = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = pool.takeConnection();
            st = connection.prepareStatement(SQL_SAVE_USER);
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
            LOGGER.log(Level.ERROR, "Request saving user failed "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
        return isCreated;

    }
}
