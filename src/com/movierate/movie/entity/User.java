package com.movierate.movie.entity;

import com.movierate.movie.type.Role;

import java.time.LocalDate;

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
    private double rating;

    public User() {
    }

    public User(long id, String login, String password, String email, Role role, int points, String photo,
                LocalDate registrDate, boolean isBanned, double rating) {
        super(id);
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.points = points;
        this.photo = photo;
        this.registrDate = registrDate;
        this.isBanned = isBanned;
        this.rating = rating;
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

    public boolean isBanned() {
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

    public void setRating(double rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", login=" + login + ", password=" + password + ", email=" + email + ", points="
                + points + ", photo=" + photo + ", isBanned=" + isBanned + ", rating=" + rating + "]";
    }

}
