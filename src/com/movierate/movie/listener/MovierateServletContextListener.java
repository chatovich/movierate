package com.movierate.movie.listener; /**
 * Created by Yultos_ on 09.12.2016.
 */

import com.movierate.movie.connection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class MovierateServletContextListener implements ServletContextListener {

    public static final Logger LOGGER = LogManager.getLogger(MovierateServletContextListener.class);

    // Public constructor is required by servlet spec
    public MovierateServletContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      LOGGER.log(Level.INFO, "Movierate application is deployed, starts working...");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool connectionPool= ConnectionPool.getInstance();
        connectionPool.closePool();
        LOGGER.log(Level.INFO, "Movierate application stopped working.");
    }


}
