package com.movierate.movie.entity;

import java.time.LocalDate;
import java.util.List;

/**
 * Class that represents entity "movie"
 */
public class Movie extends Entity{

    private String title;
    private double rating;
    private int year;
    private String plot;
    private String trailer;
    private String poster;
    private int duration;
    private int points;
    private LocalDate adding_date;
    private List<Genre> movieGenres;
    private List<Country> movieCountries;
    private List <Participant> movieParticipants;
    private List<Mark> movieMarks;
    private List<Feedback> movieFeedbacks;

    public Movie(long id, String title, double rating, int year, String plot, String trailer, String poster,
                 int duration, int points, List<Genre> movieGenres, List<Country> movieCountries,
                 List<Participant> movieParticipants, List<Mark> movieMarks, List<Feedback> movieFeedbacks, LocalDate adding_date) {
        super(id);
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.plot = plot;
        this.trailer = trailer;
        this.poster = poster;
        this.duration = duration;
        this.points = points;
        this.movieGenres = movieGenres;
        this.movieCountries = movieCountries;
        this.movieParticipants = movieParticipants;
        this.movieMarks = movieMarks;
        this.movieFeedbacks = movieFeedbacks;
        this.adding_date = adding_date;
    }

    public Movie (){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDate getAdding_date() {
        return adding_date;
    }

    public void setAdding_date(LocalDate adding_date) {
        this.adding_date = adding_date;
    }

    public List<Genre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<Genre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public List<Country> getMovieCountries() {
        return movieCountries;
    }

    public void setMovieCountries(List<Country> movieCountries) {
        this.movieCountries = movieCountries;
    }

    public List<Participant> getMovieParticipants() {
        return movieParticipants;
    }

    public void setMovieParticipants(List<Participant> movieParticipants) {
        this.movieParticipants = movieParticipants;
    }

    public List<Mark> getMovieMarks() {
        return movieMarks;
    }

    public void setMovieMarks(List<Mark> movieMarks) {
        this.movieMarks = movieMarks;
    }

    public List<Feedback> getMovieFeedbacks() {
        return movieFeedbacks;
    }

    public void setMovieFeedbacks(List<Feedback> movieFeedbacks) {
        this.movieFeedbacks = movieFeedbacks;
    }

    @Override
    public String toString(){
        return title+" "+year+"year ("+plot+") "+adding_date+" duration - "+duration+" "+movieGenres.toString()+" "+movieCountries.toString()+" "+movieParticipants.toString();
    }
}
