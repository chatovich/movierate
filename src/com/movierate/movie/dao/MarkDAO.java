package com.movierate.movie.dao;

import com.movierate.movie.entity.Mark;

import java.util.List;

/**
 * specifies methods for working with entity "mark" that its implementations should realize
 */
public interface MarkDAO {

    List<Mark> findMarksByMovieId(int id);
}
