package com.movierate.movie.entity;

import com.movierate.movie.type.FeedbackStatus;

import java.time.LocalDate;

/**
 * Class that represents entity "feedback", that was given to a movie by a user
 */
public class Feedback extends Entity{

    private Movie movie;
    private User user;
    private String text;
    private int likes;
    private LocalDate creatingDate;
    private FeedbackStatus status;

    public Feedback(long id, Movie movie, User user, String text, int likes, LocalDate creatingDate, FeedbackStatus status) {
        super(id);
        this.movie = movie;
        this.user = user;
        this.text = text;
        this.likes = likes;
        this.creatingDate = creatingDate;
        this.status = status;
    }

    public Feedback(){

    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDate getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(LocalDate creatingDate) {
        this.creatingDate = creatingDate;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
}
