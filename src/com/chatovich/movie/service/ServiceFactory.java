package com.chatovich.movie.service;

import com.chatovich.movie.service.impl.*;

/**
 * fabric for getting service objects
 */
public class ServiceFactory {

    private static final ServiceFactory factory = new ServiceFactory();

    private static final IMovieService movieService = new MovieServiceImpl();
    private static final IUserService userService = new UserServiceImpl();
    private static final IFeedbackService feedbackService = new FeedbackServiceImpl();
    private static final IParticipantService participantService = new ParticipantServiceImpl();
    private static final ICountryService countryService = new CountryServiceImpl();
    private static final IGenreService genreService = new GenreServiceImpl();

    public static ServiceFactory getInstance(){
        return factory;
    }

    public IMovieService getMovieService(){return movieService;}
    public IUserService getUserService() { return userService; }
    public IFeedbackService getFeedbackService() {return feedbackService; }
    public IParticipantService getParticipantService() { return participantService; }
    public ICountryService getCountryService() {return  countryService; }
    public IGenreService getGenreService() {return genreService;}

}
