package com.chatovich.movie.dao.impl;

import com.chatovich.movie.entity.User;
import com.chatovich.movie.type.Role;
import com.chatovich.movie.connection.ConnectionPool;
import com.chatovich.movie.connection.ProxyConnection;
import com.chatovich.movie.dao.DAO;
import com.chatovich.movie.dao.IUserDAO;
import com.chatovich.movie.exception.DAOFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects to database and operate with table "users"
 */
public class UserDAOImpl implements IUserDAO, DAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id_user,login,password,e_mail,points,photo,rating,is_banned," +
            "role, registr_date, ban_start FROM users WHERE login=?";
    private static final String SQL_SAVE_USER = "INSERT into users (login, password, e_mail, registr_date, role, photo) " +
            "VALUES (?,?,?,?,?,?)";
    private static final String SQL_FIND_LOGIN_INFO = "SELECT id_user, login, password, role FROM users WHERE login=?";
    private static final String SQL_FIND_ALL_USERS = "SELECT id_user, login FROM users WHERE role='user' ORDER BY login";
    private static final String SQL_FIND_BANNED_USERS = "SELECT id_user, login, ban_start FROM users WHERE is_banned=1";
    private static final String SQL_UPDATE_USER = "UPDATE users SET e_mail=?,password=? WHERE login=?";
    private static final String SQL_UPDATE_USER_STATUS = "UPDATE users SET is_banned=?, ban_start=? WHERE login=?";
    private static final String SQL_UPDATE_USER_WITH_PHOTO = "UPDATE users SET e_mail=?,password=?, photo=? WHERE login=?";


    /**
     * finds all information about entity 'user' in the db using his login
     * @param login user login
     * @return user
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public User findEntityByName (String login) throws DAOFailedException {
        User user = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = pool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)){
            st.setString(1,login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("e_mail"));
                user.setPoints(rs.getInt("points"));
                user.setPhoto(rs.getString("photo"));
                if (rs.getInt("is_banned")==1){
                    user.setBanned(true);
                    user.setBanStart(LocalDate.parse(rs.getString("ban_start")));
                } else {
                    user.setBanned(false);
                }
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setRegistrDate(LocalDate.parse(rs.getString("registr_date")));
            }

        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find user in db: "+e.getMessage());
        }
        return user;
    }

    /**
     * finds login info like id, login, password, role
     * @param login user login that was entered during registration
     * @return user with given id
     */
    @Override
    public User findLoginInfo(String login) throws DAOFailedException {

        User user = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = pool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_LOGIN_INFO)){
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
        }
        return user;
    }

    /**
     * saves new user into the db
     * @param user new user
     */
    @Override
    public void save(User user) throws DAOFailedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = pool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_SAVE_USER)){
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getRegistrDate().toString());
            st.setString(5, user.getRole().toString().toLowerCase());
            st.setString(6, user.getPhoto());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Saving user failed: "+e.getMessage());
        }
    }

    /**
     * updates user info
     * @param login user login
     * @param email new email
     * @param password new password
     * @param path path to the uploaded user photo
     * @throws DAOFailedException if SQLException is thrown
     */
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

    /**
     * switches user status banned-unbanned
     * @param login user login
     * @param toBan true if user need to be banned, otherwise - false
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public void changeUserStatus(String login, boolean toBan) throws DAOFailedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = pool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_UPDATE_USER_STATUS)){
                st.setString(3,login);
            if (toBan){
                st.setInt(1,1);
                st.setString(2,LocalDate.now().toString());
            } else {
                st.setInt(1,0);
                st.setString(2,null);
            }
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to update user status: "+e.getMessage());
        }
    }

    /**
     * finds all users in the db
     * @return list of users
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<User> findAllUsers() throws DAOFailedException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = pool.takeConnection();
            Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_USERS);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find all users in db: "+e.getMessage());
        }
        return users;
    }

    /**
     * finds users who were banned by admin
     * @return list of banned users
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<User> findBannedUsers() throws DAOFailedException {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        try (
            ProxyConnection connection = pool.takeConnection();
            Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(SQL_FIND_BANNED_USERS);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id_user"));
                user.setLogin(rs.getString("login"));
                user.setBanStart(LocalDate.parse(rs.getString("ban_start")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find banned user in db: "+e.getMessage());
        }
        return users;
    }
}
