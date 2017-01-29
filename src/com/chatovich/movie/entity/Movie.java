package com.chatovich.movie.entity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
    private List<Feedback> movieFeedbacks;

    public Movie(long id, String title, double rating, int year, String plot, String trailer, String poster,
                 int duration, int points, List<Genre> movieGenres, List<Country> movieCountries,
                 List<Participant> movieParticipants, List<Feedback> movieFeedbacks, LocalDate adding_date) {
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
        this.movieFeedbacks = movieFeedbacks;
        this.adding_date = adding_date;
    }

    public Movie (){}

    public Movie(long id, String title) {
        super(id);
        this.title = title;
    }

    public Movie(long id, String title, double rating) {
        super(id);
        this.title = title;
        this.rating = rating;
    }

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

    public List<Feedback> getMovieFeedbacks() {
        return movieFeedbacks;
    }

    public void setMovieFeedbacks(List<Feedback> movieFeedbacks) {
        this.movieFeedbacks = movieFeedbacks;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + super.getId() +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                ", plot='" + plot + '\'' +
                ", trailer='" + trailer + '\'' +
                ", poster='" + poster + '\'' +
                ", duration=" + duration +
                ", points=" + points +
                ", adding_date=" + adding_date +
                ", movieGenres=" + movieGenres +
                ", movieCountries=" + movieCountries +
                ", movieParticipants=" + movieParticipants +
                ", movieFeedbacks=" + movieFeedbacks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass()!=o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.rating, rating) == 0 &&
                super.getId() == movie.getId() &&
                year == movie.year &&
                duration == movie.duration &&
                points == movie.points &&
                Objects.equals(title, movie.title) &&
                Objects.equals(plot, movie.plot) &&
                Objects.equals(trailer, movie.trailer) &&
                Objects.equals(poster, movie.poster) &&
                Objects.equals(adding_date, movie.adding_date) &&
                Objects.equals(movieGenres, movie.movieGenres) &&
                Objects.equals(movieCountries, movie.movieCountries) &&
                Objects.equals(movieParticipants, movie.movieParticipants) &&
                Objects.equals(movieFeedbacks, movie.movieFeedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), title, rating, year, plot, trailer, poster, duration, points, adding_date, movieGenres, movieCountries, movieParticipants, movieFeedbacks);
    }
}
