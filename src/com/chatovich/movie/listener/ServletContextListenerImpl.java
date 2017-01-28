package com.chatovich.movie.listener;

import com.chatovich.movie.connection.ConnectionPool;
import com.chatovich.movie.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ServletContextListenerImpl implements ServletContextListener {

    public static final Logger LOGGER = LogManager.getLogger(ServletContextListenerImpl.class);

    public void contextInitialized(ServletContextEvent sce) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.controlBan();
      LOGGER.log(Level.INFO, "Movierate application is deployed, starts working...");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool connectionPool= ConnectionPool.getInstance();
        connectionPool.closePool();
        LOGGER.log(Level.INFO, "Movierate application stopped working.");
    }


}
