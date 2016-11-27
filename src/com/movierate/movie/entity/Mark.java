package com.movierate.movie.entity;

/**
 * Class that represents entity "mark", that was given to a movie by a user
 */
public class Mark extends Entity{

    private Movie movie;
    private int mark;
    private User user;

    public Mark(long id, Movie movie, int mark, User user) {
        super(id);
        this.movie = movie;
        this.mark = mark;
        this.user = user;
    }

    public Mark(){}

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
