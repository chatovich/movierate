package com.chatovich.movie.service.impl;

import com.chatovich.movie.dao.*;
import com.chatovich.movie.entity.Country;
import com.chatovich.movie.entity.Genre;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.exception.RollbackFailedException;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IMovieService;
import com.chatovich.movie.util.QueryUtil;

import java.time.LocalDate;
import java.util.*;

/**
 * Class that encapsulates logic connected with entity "movie" and represents intermediate layer between database and client
 */
public class MovieServiceImpl implements IMovieService {

    /**
     * creates a new object Movie using data gotten by dao
     * @param id id of the movie to save
     * @return created movie object
     * @throws ServiceException if DAOFailedException is thrown
     */
    public Movie findMovieById(long id) throws ServiceException {
        Movie movie;
        IMovieDAO movieDAOImpl = DAOFactory.getInstance().getMovieDAO();
        IGenreDAO genreDAOImpl = DAOFactory.getInstance().getGenreDAO();
        ICountryDAO countryDAOImpl = DAOFactory.getInstance().getCountryDAO();
        IParticipantDAO participantDAOImpl = DAOFactory.getInstance().getParticipantDAO();
        IFeedbackDAO feedbackDAOImpl = DAOFactory.getInstance().getFeedbackDAO();
        try {
                movie = movieDAOImpl.findEntityById(id);
                movie.setMovieGenres(genreDAOImpl.findGenresByMovieId(id));
                movie.setMovieCountries(countryDAOImpl.findCountriesByMovieId(id));
                movie.setMovieParticipants(participantDAOImpl.findParticipantsByMovieId(id));
                movie.setMovieFeedbacks(feedbackDAOImpl.findFeedbacksByMovieId(id));
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }

        return movie;

    }

    /**
     * creates a new object 'movie' using parameters entered by admin
     * @param parameters movie description entered by admin
     * @param path path to movie poster
     * @throws ServiceException if DAOFailedException is thrown
     */
    public void createMovie(Map<String,String[]> parameters, String path) throws ServiceException {

        Movie movie = new Movie();
        List<Genre> genres = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();
        IGenreDAO genreDAO = DAOFactory.getInstance().getGenreDAO();
        ICountryDAO countryDAO = DAOFactory.getInstance().getCountryDAO();
        IParticipantDAO participantDAO = DAOFactory.getInstance().getParticipantDAO();

        try {
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                switch (entry.getKey()){
                    case "id_movie": movie.setId(Long.parseLong(entry.getValue()[0]));
                        break;
                    case "title": movie.setTitle(entry.getValue()[0]);
                        break;
                    case "year": movie.setYear(Integer.parseInt(entry.getValue()[0]));
                        break;
                    case "plot": movie.setPlot(entry.getValue()[0]);
                        break;
                    case "duration": movie.setDuration(Integer.parseInt(entry.getValue()[0]));
                        break;
                    case "genre":
                        for (String s : entry.getValue()) {
                            genres.add(genreDAO.findEntityByName(s));
                        }
                        break;
                    case "country":
                        for (String s : entry.getValue()) {
                            countries.add(countryDAO.findEntityByName(s));
                        }
                        break;
                    case "actor":
                        for (String s : entry.getValue()) {
                            for (Participant participant : participantDAO.findEntityByName(s)) {
                                if ("actor".equals(String.valueOf(participant.getProfession()).toLowerCase())){
                                    participants.add(participant);
                                }
                            }
                        }
                        break;
                    case "director":
                        for (String s : entry.getValue()) {
                            for (Participant participant : participantDAO.findEntityByName(s)) {
                                if ("director".equals(String.valueOf(participant.getProfession()).toLowerCase())){
                                    participants.add(participant);
                                }
                            }
                        }
                        break;
                }
            }
            movie.setMovieGenres(genres);
            movie.setMovieCountries(countries);
            movie.setMovieParticipants(participants);
            IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();

            if (movie.getId()==0){
                movie.setPoster(path);
                movie.setAddingDate(LocalDate.now());
            } else {
                Movie oldMovie = movieDAO.findEntityById(movie.getId());
                movie.setAddingDate(oldMovie.getAddingDate());
                if (path==null){
                    movie.setPoster(oldMovie.getPoster());
                } else {
                    movie.setPoster(path);
                }
            }
            movieDAO.save(movie);
        } catch (DAOFailedException | RollbackFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * checks whether such movie already exists
     * @param title movie title
     * @return true if movie exists, otherwise - false
     * @throws ServiceException if DAOFailedException is thrown
     */
    public boolean movieExists (String title) throws ServiceException {
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        boolean movieExists;
        try {
            movieExists = movieDAO.checkMovieExists(title);
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return movieExists;
    }

    /**
     * gets all movies
     * @return list of movies
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Movie> getAllMovies () throws ServiceException {
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        List<Movie> movies;
        try {
            movies = movieDAO.findAll();
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return movies;
    }

    /**
     * finds movies according to the parameters entered by user
     * @param parameters entered by user
     * @param start index movie number, from which search is gonna start
     * @param moviesPerPage movies' quantity tht is displayed per page
     * @return list of movies
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Movie> filteredMovieSearch (Map <String, String[]> parameters, int start, int moviesPerPage) throws ServiceException {

        String query = QueryUtil.buildMovieSearchQuery(parameters);
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        List<Movie> movies;
        try {
            movies = movieDAO.findFilteredMovies(query, start, moviesPerPage);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return movies;
    }

    /**
     * gets total quantity of movies without limit for movies per page
     * @return movies' quantity
     */
    public int getMovieQuantity (){
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        return movieDAO.getMovieQuantity();
    }

    /**
     * deletes movie from the db
     * @param idMovie movie id
     * @throws ServiceException if DAOFailedException is thrown
     */
    public void deleteMovie(long idMovie) throws ServiceException {
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        try {
            movieDAO.deleteMovie(idMovie);
        } catch (DAOFailedException | RollbackFailedException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * finds top movies for loading main page
     * @return list of movies
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Movie> findTopMovies() throws ServiceException {
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        List<Movie> topMovies;
        try {
            topMovies = movieDAO.findTopMovies();

        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return topMovies;
    }

    @Override
    public List<Movie> findMovieByTitle(String title, int start, int moviesPerPage) throws ServiceException {
        IMovieDAO movieDAO = DAOFactory.getInstance().getMovieDAO();
        List<Movie> movies;
        try {
            movies = movieDAO.findMovieByTitle(title.trim(), start, moviesPerPage);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return movies;
    }
}
