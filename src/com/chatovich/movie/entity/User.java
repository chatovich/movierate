package com.chatovich.movie.entity;

import com.chatovich.movie.type.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents entity "user"
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
        return "User{" +
                "id=" + super.getId() +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", points=" + points +
                ", photo='" + photo + '\'' +
                ", registrDate=" + registrDate +
                ", isBanned=" + isBanned +
                ", banStart=" + banStart +
                ", rating=" + rating +
                ", userFeedbacks=" + userFeedbacks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass()!=o.getClass()) return false;
        User user = (User) o;
        return points == user.points &&
                super.getId() == user.getId() &&
                isBanned == user.isBanned &&
                Double.compare(user.rating, rating) == 0 &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                role == user.role &&
                Objects.equals(photo, user.photo) &&
                Objects.equals(registrDate, user.registrDate) &&
                Objects.equals(banStart, user.banStart) &&
                Objects.equals(userFeedbacks, user.userFeedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), login, password, email, role, points, photo, registrDate, isBanned, banStart, rating, userFeedbacks);
    }
}
