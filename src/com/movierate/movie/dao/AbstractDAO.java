package com.movierate.movie.dao;

import com.movierate.movie.entity.Entity;

import java.util.List;

/**
 * Created by Yultos_ on 27.11.2016
 */
public abstract class AbstractDAO <T extends Entity>{

    public abstract List<T> findAll ();
    public abstract List<T> findEntityByName(String name);
    public abstract List<T> findEntityById(int id);
    public abstract boolean create(T entity);
    public abstract boolean delete(T entity);

}
