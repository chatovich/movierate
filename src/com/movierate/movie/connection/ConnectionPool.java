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
    private BlockingQueue<ProxyConnection> connections;
    private static ConnectionPool instance;
    private static AtomicBoolean poolExists = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    public ConnectionPool (){
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DataBaseInfo.DB_PROPERTY);
            Properties properties = new Properties();
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = bundle.getString("db.url");
            properties.setProperty("url", bundle.getString("db.url"));
            properties.setProperty("user", bundle.getString("db.user"));
            properties.setProperty("password", bundle.getString("db.password"));
            properties.setProperty("poolsize", bundle.getString("db.poolsize"));
//            properties.setProperty("url", bundle.getString("db.poolsize"));
 //           properties.setProperty("useClientPrepStmts", bundle.getString("db.useClientPrepStmts"));

            int poolsize = Integer.parseInt(bundle.getString("db.poolsize"));
            connections = new ArrayBlockingQueue<ProxyConnection>(poolsize);

            for (int i = 0; i < poolsize; i++) {
                Connection connection = DriverManager.getConnection(url, properties);
                //test
                if (!connection.isClosed()){
                    LOGGER.log(Level.DEBUG, i+" connected");
                }
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connections.put(proxyConnection);
            }
        } catch (MissingResourceException|SQLException|InterruptedException e){
            LOGGER.log(Level.ERROR, "Impossible to connect with database: "+e.getMessage());
            throw new RuntimeException();
        }

    }

    public static ConnectionPool getInstance(){
        if (!poolExists.get()){
            lock.lock();
            try{
                if (instance==null){
                    instance = new ConnectionPool();
                    poolExists.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection takeConnection(){
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Impossible to take a connection: "+e.getMessage());
        }
        return proxyConnection;
    }

    public void releaseConnection(ProxyConnection proxyConnection){
        if (proxyConnection !=null){
            try {
                connections.put(proxyConnection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Impossible to release the connection: "+e.getMessage());
            }
        }
    }

    public void closePool(){
        for (int i = 0; i < connections.size(); i++) {
            ProxyConnection proxyConnection = null;
            try {
                proxyConnection = connections.take();
                proxyConnection.close();
                if (proxyConnection.isClosed()){
                    LOGGER.log(Level.DEBUG, " disconnected");
                }
            } catch (InterruptedException|SQLException e) {
                LOGGER.log(Level.ERROR, "Impossible to close the pool: "+e.getMessage());
            }

        }
    }
}
