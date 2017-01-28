package com.chatovich.movie.service.impl;

import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.dao.DAOFactory;
import com.chatovich.movie.dao.IFeedbackDAO;
import com.chatovich.movie.dao.IUserDAO;
import com.chatovich.movie.dao.impl.FeedbackDAOImpl;
import com.chatovich.movie.dao.impl.UserDAOImpl;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.exception.HashPasswordFailedException;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.type.Role;
import com.chatovich.movie.util.PasswordHash;
import com.chatovich.movie.util.RatingCalculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * Class that encapsulates logic connected with entity "user" and represents intermediate layer between database and client
 */
public class UserServiceImpl implements IUserService{

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    /**
     * creates a new Object "User" using tha data from inout form on registration page
     * @param parameters map with all the parameters of the input form, where key is a name of the parameter, value - data that was inserted by user
     * @param filePath absolute path to the photo uploaded by user
     * @throws ServiceException if DAOFailedException is thrown
     */
    public void createUser (Map<String, String[]> parameters, String filePath) throws ServiceException {
        User user = new User();
        user.setPhoto(filePath);
        user.setRole(Role.USER);
        user.setRegistrDate(LocalDate.now());
        IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
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
            userDAO.save(user);
        } catch (DAOFailedException|HashPasswordFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * checks whether login entered by user during registration is available
     * @param login login entered by user
     * @return true if login available, otherwise - false
     * @throws ServiceException if DAOFailedException is thrown
     */
    public boolean loginAvailable (String login) throws ServiceException {
        IUserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
        User user;
        try {
            user = userDAOImpl.findLoginInfo(login);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return user.getLogin()==null;
    }

    /**
     * gets user using his login
     * @param login user login
     * @return user
     * @throws ServiceException if DAOFailedException is thrown
     */
    public User getUser(String login) throws ServiceException {

        IUserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
        FeedbackDAOImpl feedbackDAO = new FeedbackDAOImpl();
        User user;
        try {
            user = userDAOImpl.findEntityByName(login);
            user.setUserFeedbacks(feedbackDAO.findFeedbacksByUserId(user.getId()));
        } catch (DAOFailedException e) {
            throw  new ServiceException(e);
        }
        return user;
    }

    /**
     * gets login info (id, login, password, role)
     * @param parameters parameters from http request
     * @return user
     * @throws ServiceException if DAOFailedException is thrown
     */
    public User getLoginInfo (Map<String, String[]> parameters) throws ServiceException {

        String login = parameters.get(Parameters.LOGIN)[0];
        IUserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
        User user;
        try {
            user = userDAOImpl.findLoginInfo(login);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * updates user in the db
     * @param parameters from http request
     * @param path path to user photo
     * @throws ServiceException if DAOFailedException is thrown
     */
    public void updateUser (Map<String, String[]> parameters, String path) throws ServiceException {

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
        IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            userDAO.updateUser(login, email, PasswordHash.getHashPassword(password), path);
        } catch (DAOFailedException|HashPasswordFailedException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * calculates user rating using his marks to movies and movies' rating
     * @param id user id
     * @return user rating
     * @throws ServiceException if DAOFailedException is thrown
     */
    public double defineUserRating(String id) throws ServiceException {
        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        List<Feedback> userFeedbacks = null;
        try {
            userFeedbacks = feedbackDAO.findUserMarks(Long.parseLong(id));
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        double userRating = 0.;
        if (!userFeedbacks.isEmpty()) {
            RatingCalculator ratingCalculator = new RatingCalculator();
            userRating = ratingCalculator.calcUserRating(userFeedbacks);
        }
        return userRating;
    }

    /**
     * gets user with updated feedbacks' list
     * @param user user whose feedbacks are updated
     * @return user with updated feedbacks
     * @throws ServiceException if DAOFailedException is thrown
     */
    public User updateUserFeedbacks (User user) throws ServiceException {
        IFeedbackDAO feedbackDAO = DAOFactory.getInstance().getFeedbackDAO();
        try {
            user.setUserFeedbacks(feedbackDAO.findFeedbacksByUserId(user.getId()));
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * gets all users
     * @return list of users
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<User> getAllUsers() throws ServiceException {
        IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        List <User> users;
        try {
            users = userDAO.findAllUsers();
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    public void changeUserStatus (String login, boolean toBan) throws ServiceException {
        IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            userDAO.changeUserStatus(login,toBan);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * controls banned users every day and unban them if dan period is more than 10 days
     */
    public void controlBan(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
                try {
                    List<User> bannedUsers = userDAO.findBannedUsers();
                    for (User user : bannedUsers) {
                        int days = Period.between(user.getBanStart(),LocalDate.now()).getDays();
                        if (days>Parameters.BAN_DAYS){
                            userDAO.changeUserStatus(user.getLogin(),false);
                        }
                    }
                } catch (DAOFailedException e) {
                    LOGGER.log(Level.ERROR, e.getMessage());
                }

            }
        };
        new Timer(true).scheduleAtFixedRate(task, 0,60000);

    }


}
