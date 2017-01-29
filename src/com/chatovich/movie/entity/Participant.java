package com.chatovich.movie.entity;

import com.chatovich.movie.type.Profession;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + super.getId() +
                "name='" + name + '\'' +
                ", profession=" + profession +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass()!=o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(name, that.name) &&
                profession == that.profession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), name, profession);
    }
}
