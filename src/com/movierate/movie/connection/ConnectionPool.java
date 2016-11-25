package com.movierate.movie.connection;

import com.movierate.movie.constant.DataBaseInfo;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
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
 * Created by Yultos_ on 23.11.2016
 */
public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private BlockingQueue<ConnectionWrapper> connections;
    private ConnectionPool instance;
    private AtomicBoolean poolExists;
    private ReentrantLock lock;

    public ConnectionPool (){
        try {
            poolExists = new AtomicBoolean(false);
            lock = new ReentrantLock();
            ResourceBundle bundle = ResourceBundle.getBundle(DataBaseInfo.DB_PROPERTY);
            Properties properties = new Properties();
            String url = bundle.getString("db.url");
            properties.setProperty("url", bundle.getString("db.url"));
            properties.setProperty("user", bundle.getString("db.user"));
            properties.setProperty("password", bundle.getString("db.password"));
            properties.setProperty("poolsize", bundle.getString("db.poolsize"));
            properties.setProperty("url", bundle.getString("db.poolsize"));
//            Driver driver = new FabricMySQLDriver();
//            DriverManager.registerDriver(driver);
            int poolsize = Integer.parseInt(bundle.getString("db.poolsize"));
            connections = new ArrayBlockingQueue<ConnectionWrapper>(poolsize);

            for (int i = 0; i < poolsize; i++) {
                Connection connection = DriverManager.getConnection(url, properties);
                //test
                if (!connection.isClosed()){
                    LOGGER.log(Level.DEBUG, i+" connected");
                }
                ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
                connections.put(connectionWrapper);
            }
        } catch (MissingResourceException|SQLException|InterruptedException e){
            LOGGER.log(Level.ERROR, "Impossible to connect with database: "+e.getMessage());
        }

    }

    public ConnectionPool getInstance(){
        if (!poolExists.get()){
            lock.lock();
            try{
                if (instance==null){
                    instance = new ConnectionPool();

                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ConnectionWrapper takeConnection(){
        ConnectionWrapper connectionWrapper = null;
        try {
            connectionWrapper = connections.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Impossible to take a connection: "+e.getMessage());
        }
        return connectionWrapper;
    }

    public void releaseConnection(ConnectionWrapper connectionWrapper){
        if (connectionWrapper!=null){
            try {
                connections.put(connectionWrapper);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Impossible to release the connection: "+e.getMessage());
            }
        }
    }

    public void closePool(){
        while (!connections.isEmpty()){
            ConnectionWrapper connectionWrapper = null;
            try {
                connectionWrapper = connections.take();
                connectionWrapper.close();
                if (connectionWrapper.isClosed()){
                    LOGGER.log(Level.DEBUG, " disconnected");
                }
            } catch (InterruptedException|SQLException e) {
                LOGGER.log(Level.ERROR, "Impossible to close the pool: "+e.getMessage());
            }

        }

    }
}
