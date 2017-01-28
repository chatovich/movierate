package com.chatovich.movie.connection;

import com.chatovich.movie.constant.DataBaseInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that creates connection pool and organizes its work
 */
public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private BlockingQueue<ProxyConnection> connections;
    private static ConnectionPool instance;
    private static AtomicBoolean poolExists = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    public ConnectionPool (){
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DataBaseInfo.DB_PROPERTY);
            Properties properties = new Properties();
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = bundle.getString(DataBaseInfo.DB_URL);
            properties.setProperty(DataBaseInfo.URL, url);
            properties.setProperty(DataBaseInfo.USER, bundle.getString(DataBaseInfo.DB_USER));
            properties.setProperty(DataBaseInfo.PASSWORD, bundle.getString(DataBaseInfo.DB_PASSWORD));
            int poolsize=0;
            if (!bundle.getString(DataBaseInfo.DB_POOLSIZE).isEmpty()) {
                properties.setProperty(DataBaseInfo.POOLSIZE, bundle.getString(DataBaseInfo.DB_POOLSIZE));
                poolsize = Integer.parseInt(bundle.getString(DataBaseInfo.DB_POOLSIZE));
            } else {
                properties.setProperty(DataBaseInfo.POOLSIZE, DataBaseInfo.DEFAULT_POOLSIZE);
                poolsize = Integer.parseInt(DataBaseInfo.DEFAULT_POOLSIZE);
            }
            connections = new ArrayBlockingQueue<ProxyConnection>(poolsize);

            while (connections.size()<poolsize){
                try {
                    Connection connection = DriverManager.getConnection(url, properties);
                    ProxyConnection proxyConnection = new ProxyConnection(connection);
                    connections.put(proxyConnection);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.ERROR, "Impossible to connect with database: "+e.getMessage());
                }
            }

        } catch (MissingResourceException|SQLException e){
            LOGGER.log(Level.FATAL, "Impossible to connect with database: "+e.getMessage());
            throw new RuntimeException();
        }

    }
    /**
     * creates new Connection pool entity if it doesn't exist, and return link to existing if it already exists.
     * @return ConnectionPool instance
     */
    public static ConnectionPool getInstance(){
        if (!poolExists.get()){
            lock.lock();
            try{
                if (instance==null){
                    instance = new ConnectionPool();
                    poolExists.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * takes connection from connection pool to execute specified DAO method
     * @return connection to database from connection pool
     */
    public ProxyConnection takeConnection(){
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Impossible to take a connection: "+e.getMessage());
        }
        return proxyConnection;
    }

    /**
     * returns connection that was used to execute specified DAO method to connection pool
     * @param proxyConnection connection that should be returned to connection pool
     */
    public void releaseConnection(ProxyConnection proxyConnection){
        if (proxyConnection !=null){
            try {
                if (!proxyConnection.getAutoCommit()){
                    proxyConnection.setAutoCommit(true);
                }
                connections.put(proxyConnection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Impossible to release the connection: "+e.getMessage());
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Problem with connection: "+e.getMessage());
            }
        }
    }

    /**
     * closes all connections to database in connection pool
     */
    public void closePool(){
        for (int i = 0; i < connections.size(); i++) {
            ProxyConnection proxyConnection = null;
            try {
                proxyConnection = connections.take();
                proxyConnection.realClose();
                if (proxyConnection.isClosed()){
                    LOGGER.log(Level.DEBUG, " disconnected");
                }
            } catch (InterruptedException|SQLException e) {
                LOGGER.log(Level.ERROR, "Impossible to close the pool: "+e.getMessage());
            }

        }
    }
}
