package com.movierate.movie.dao;

import com.movierate.movie.entity.Feedback;
import com.movierate.movie.entity.User;

import java.util.List;

/**
 * specifies methods for working with entity "feedback" that its implementations should realize
 */
public interface FeedbackDAO {

    boolean save(Feedback feedback);
}
