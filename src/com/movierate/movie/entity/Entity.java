package com.movierate.movie.entity;

import java.io.Serializable;

/**
 * Created by Yultos_ on 27.11.2016
 */
public abstract class Entity implements Serializable, Cloneable {

    private long id;

    public Entity(long id) {
        this.id = id;
    }

    public Entity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
