package com.chatovich.movie.dao;

import com.chatovich.movie.dao.impl.*;

/**
 * fabric for getting DAO objects
 */
public class DAOFactory {

    private static final DAOFactory daoFactory = new DAOFactory();

    private static final IUserDAO userDAO = new UserDAOImpl();
    private static final IMovieDAO movieDAO = new MovieDAOImpl();
    private static final IParticipantDAO participantDAO = new ParticipantDAOImpl();
    private static final IFeedbackDAO feedbackDAO = new FeedbackDAOImpl();
    private static final IGenreDAO genreDAO = new GenreDAOImpl();
    private static final ICountryDAO countryDAO = new CountryDAOImpl();

    public static DAOFactory getInstance(){
        return daoFactory;
    }

    public IUserDAO getUserDAO() {return userDAO;}
    public IMovieDAO getMovieDAO() {return movieDAO;}
    public IParticipantDAO getParticipantDAO() { return participantDAO;}
    public IFeedbackDAO getFeedbackDAO() {return feedbackDAO;}
    public IGenreDAO getGenreDAO() {return genreDAO;}
    public ICountryDAO getCountryDAO() { return countryDAO; }

}
