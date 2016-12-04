package com.movierate.movie.dao;

import com.movierate.movie.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.movierate.movie.util.PasswordHash.LOGGER;

/**
 * root of the DAO hierarchy
 */
public interface DAOI <T extends Entity> {

    Logger LOGGER = LogManager.getLogger(DAOI.class);

    List<T> findEntityById(int id);

    default void close (PreparedStatement st){
        if (st!=null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Problem with closing a statement: "+e.getMessage());
            }
        }
    }
}
