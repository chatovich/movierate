package com.movierate.movie.entity;

import com.movierate.movie.type.Profession;

/**
 * Class that represents entity "participant" (actors and directors)
 */
public class Participant extends Entity {

    private String name;
    private Profession profession;

    public Participant(long id, String name, Profession profession) {
        super(id);
        this.name = name;
        this.profession = profession;
    }

    public Participant(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
