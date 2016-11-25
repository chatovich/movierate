package com.movierate.movie.connection;

import com.movierate.movie.constant.DataBaseInfo;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Yultos_ on 23.11.2016
 */
public class Check {
    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool();
        pool.closePool();
    }
}
