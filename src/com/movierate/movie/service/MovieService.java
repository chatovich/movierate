package com.movierate.movie.service;

import com.movierate.movie.constant.Parameters;
import com.movierate.movie.dao.impl.*;
import com.movierate.movie.entity.*;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.RollbackFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.util.QueryUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that encapsulates logic connected with entity "movie" and represents intermediate layer between database and client
 */
public class MovieService {

    /**
     * creates a new object Movie using data gotten by dao
     * @param id id of the movie to save
     * @return created movie object
     */
    public Movie findMovieById(int id) throws DAOFailedException {
        Movie movie = null;
        MovieDAOImpl movieDAOImpl = new MovieDAOImpl();
        if (movieDAOImpl.findEntityById(id)!=null){
            movie = movieDAOImpl.findEntityById(id);
            GenreDAOImpl genreDAOImpl = new GenreDAOImpl();
            CountryDAOImpl countryDAOImpl = new CountryDAOImpl();
            ParticipantDAOImpl participantDAOImpl = new ParticipantDAOImpl();
//            MarkDAOImpl markDAOImpl = new MarkDAOImpl();
            FeedbackDAOImpl feedbackDAOImpl = new FeedbackDAOImpl();

            movie.setMovieGenres(genreDAOImpl.findGenresByMovieId(id));
            movie.setMovieCountries(countryDAOImpl.findCountriesByMovieId(id));
            movie.setMovieParticipants(participantDAOImpl.findParticipantsByMovieId(id));
//            movie.setMovieMarks(markDAOImpl.findMarksByMovieId(id));
            movie.setMovieFeedbacks(feedbackDAOImpl.findFeedbacksByMovieId(id));
        }

        return movie;

    }

    public void createMovie(Map<String,String[]> parameters, String path) throws DAOFailedException {

        Movie movie = new Movie();
        List<Genre> genres = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        CountryDAOImpl countryDAO = new CountryDAOImpl();
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();

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
        MovieDAOImpl movieDAO = new MovieDAOImpl();

        if (movie.getId()==0){
            movie.setPoster(path);
            movie.setAdding_date(LocalDate.now());
        } else {
            Movie oldMovie = movieDAO.findEntityById(movie.getId());
            System.out.println(oldMovie);
            movie.setAdding_date(oldMovie.getAdding_date());
            if (path==null){
                movie.setPoster(oldMovie.getPoster());
            } else {
                movie.setPoster(path);
            }
        }
        System.out.println(movie);
        movieDAO.save(movie);
    }

    public boolean movieExists (String title){
        MovieDAOImpl movieDAO = new MovieDAOImpl();
        return movieDAO.checkMovieExists(title);
    }

    public List<Movie> getAllMovies () throws DAOFailedException {
        MovieDAOImpl movieDAO = new MovieDAOImpl();
        return movieDAO.findAll();
    }

    public List<Movie> filteredMovieSearch (Map <String, String[]> parameters, int start, int moviesPerPage) throws ServiceException {

        String query = QueryUtil.buildMovieSearchQuery(parameters);
//        System.out.println(query);
        MovieDAOImpl movieDAO = new MovieDAOImpl();
        List<Movie> movies = new ArrayList<>();
        try {
            movies = movieDAO.findFilteredMovies(query, start, moviesPerPage);
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return movies;
    }

    public int getMovieQuantity (){
        MovieDAOImpl movieDAO = new MovieDAOImpl();
        System.out.println("service"+movieDAO.getMovieQuantity());
        return movieDAO.getMovieQuantity();
    }

    public void deleteMovie(long id_movie) throws DAOFailedException, RollbackFailedException {
        MovieDAOImpl movieDAO = new MovieDAOImpl();
        Movie movie = movieDAO.findEntityById(id_movie);
        String poster = movie.getPoster();
        movieDAO.deleteMovie(id_movie);

    }
}
