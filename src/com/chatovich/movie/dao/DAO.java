package com.chatovich.movie.dao;

import com.chatovich.movie.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * root of the DAO hierarchy
 */
public interface DAO<T extends Entity> {

   public static final Logger LOGGER = LogManager.getLogger(DAO.class);

    /**
     * default method to close statement
     * @param st statement to be closed
     */
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
