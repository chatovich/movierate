package com.chatovich.movie.entity;

import java.util.Objects;

/**
 * Class that represents entity movie "genre"
 */
public class Genre extends Entity {

    private String genreName;

    public Genre(long id, String genreName) {
        super(id);
        this.genreName = genreName;
    }

    public Genre(){}

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + super.getId() +
                "genreName='" + genreName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass()!=this.getClass()) return false;
        Genre genre = (Genre) o;
        return super.getId() == genre.getId() &&
                Objects.equals(genreName, genre.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), genreName);
    }
}
