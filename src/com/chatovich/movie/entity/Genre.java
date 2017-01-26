package com.chatovich.movie.entity;

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
    public String toString(){
        return super.getId() + " - " +genreName;
    }
}
