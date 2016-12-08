package com.movierate.movie.dao;

import com.movierate.movie.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * root of the DAO hierarchy
 */
public interface DAO<T extends Entity> {

    Logger LOGGER = LogManager.getLogger(DAO.class);

    default void close (Statement st){
        if (st!=null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Problem with closing a statement: "+e.getMessage());
            }
        }
    }
}
