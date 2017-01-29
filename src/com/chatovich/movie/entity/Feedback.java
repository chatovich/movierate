package com.chatovich.movie.entity;

import com.chatovich.movie.type.FeedbackStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

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
    private int mark;

    public Feedback(long id, Movie movie, User user, String text, int likes, LocalDate creatingDate, FeedbackStatus status, int mark) {
        super(id);
        this.movie = movie;
        this.user = user;
        this.text = text;
        this.likes = likes;
        this.creatingDate = creatingDate;
        this.status = status;
        this.mark = mark;
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass()!=o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return super.getId() == feedback.getId() &&
                likes == feedback.likes &&
                mark == feedback.mark &&
                Objects.equals(movie, feedback.movie) &&
                Objects.equals(user, feedback.user) &&
                Objects.equals(text, feedback.text) &&
                Objects.equals(creatingDate, feedback.creatingDate) &&
                status == feedback.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), movie, user, text, likes, creatingDate, status, mark);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + super.getId() +
                "movie=" + movie +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", creatingDate=" + creatingDate +
                ", status=" + status +
                ", mark=" + mark +
                '}';
    }
}
