package com.movierate.movie.entity;

import com.movierate.movie.type.Role;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yultos_ on 27.11.2016
 */
public class User extends Entity{

    private String login;
    private String password;
    private String email;
    private Role role;
    private int points;
    private String photo;
    private LocalDate registrDate;
    private boolean isBanned;
    private LocalDate banStart;
    private double rating;
    private List<Feedback> userFeedbacks;

    public User() {
    }

    public User(long id, String login, String password, String email, Role role, int points, String photo,
                LocalDate registrDate, boolean isBanned, LocalDate banStart, double rating, List<Feedback> userFeedbacks) {
        super(id);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.points = points;
        this.photo = photo;
        this.registrDate = registrDate;
        this.isBanned = isBanned;
        this.banStart = banStart;
        this.rating = rating;
        this.userFeedbacks = userFeedbacks;
    }

    public User(long id, String login, String photo) {
        super(id);
        this.login = login;
        this.photo = photo;
    }

    public User(long id, String login) {
        super(id);
        this.login = login;
    }

    public User(long id) {
        super(id);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public int getPoints() {
        return points;
    }

    public String getPhoto() {
        return photo;
    }

    public LocalDate getRegistrDate() {
        return registrDate;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public double getRating() {
        return rating;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRegistrDate(LocalDate registrDate) {
        this.registrDate = registrDate;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public LocalDate getBanStart() {
        return banStart;
    }

    public void setBanStart(LocalDate banStart) {
        this.banStart = banStart;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Feedback> getUserFeedbacks() {
        return userFeedbacks;
    }

    public void setUserFeedbacks(List<Feedback> userFeedbacks) {
        this.userFeedbacks = userFeedbacks;
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", login=" + login + ", password=" + password + ", email=" + email + ", points="
                + points + ", photo=" + photo + ", isBanned=" + isBanned + ", rating=" + rating + "]";
    }

}
