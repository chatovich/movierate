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

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects to database and operate with table "users"
 */
public class UserDAOImpl implements UserDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT id_user,login,password,e_mail,points,photo,rating,isBanned," +
            "role, registr_date FROM users WHERE login=?";
    public static final String SQL_SAVE_USER = "INSERT into users (login, password, e_mail, registr_date, role, photo) " +
            "VALUES (?,?,?,?,?,?)";
    public static final String SQL_FIND_LOGIN_INFO = "SELECT id_user, login, password, role FROM users WHERE login=?";
    public static final String SQL_FIND_ALL_USERS = "SELECT id_user, login FROM users WHERE role='user'";
    public static final String SQL_UPDATE_USER = "UPDATE users SET e_mail=?,password=? WHERE login=?";
    public static final String SQL_UPDATE_USER_STATUS = "UPDATE users SET isBanned=? WHERE login=?";
    public static final String SQL_UPDATE_USER_WITH_PHOTO = "UPDATE users SET e_mail=?,password=?, photo=? WHERE login=?";


    @Override
    public User findEntityByName (String login) throws DAOFailedException {
        User user = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = pool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            st.setString(1,login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("e_mail"));
                user.setPoints(rs.getInt("points"));
                user.setPhoto(rs.getString("photo"));
                //change to double!!!
                user.setRating(rs.getInt("rating"));
                if (rs.getInt("isBanned")==1){
                    user.setBanned(true);
                } else {
                    user.setBanned(false);
                }
                //when in db string instead of enum!!
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setRegistrDate(LocalDate.parse(rs.getString("registr_date")));
            }

        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find user in db: "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
        return user;
    }

    /**
     *
     * @param login user login that was entered during registration
     * @return user with given id
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
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
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

    @Override
    public void updateUser(String login, String email, String password, String path) throws DAOFailedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = pool.takeConnection();
            if (path==null){
                st = connection.prepareStatement(SQL_UPDATE_USER);
                st.setString(3,login);
            } else {
                st = connection.prepareStatement(SQL_UPDATE_USER_WITH_PHOTO);
                st.setString(3,path);
                st.setString(4,login);
            }
            st.setString(1, email);
            st.setString(2, password);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to update user: "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void changeUserStatus(String login, boolean toBan) throws DAOFailedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try {
            connection = pool.takeConnection();
                st = connection.prepareStatement(SQL_UPDATE_USER_STATUS);
                st.setString(2,login);
            if (toBan){
                st.setInt(1,1);
            } else {
                st.setInt(1,0);
            }
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to update user status: "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> findAllUsers() throws DAOFailedException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        Statement st = null;
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_USERS);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find all user in db: "+e.getMessage());
        } finally {
            close(st);
            pool.releaseConnection(connection);
        }
        return users;
    }
}
