package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.CountryDAO;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.entity.Country;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "countries"
 */
public class CountryDAOImpl implements CountryDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(CountryDAOImpl.class);
    public static final String SQL_FIND_COUNTRIES_OF_MOVIE = "SELECT * FROM countries WHERE id_country IN " +
            "(SELECT id_country FROM movies_countries WHERE id_movie=?)";
    public static final String SQL_FIND_COUNTRY_BY_NAME = "SELECT id_country, country FROM countries WHERE country=?";
    public static final String SQL_FIND_ALL_COUNTRIES = "SELECT id_country, country FROM countries";



    /**
     * finds all countries of the movie by movie id
     * @param id id of the movie
     * @return list containing countries of the movie
     */
    @Override
    public List<Country> findCountriesByMovieId(int id) {
        List<Country> countriesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_COUNTRIES_OF_MOVIE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id_country"));
                country.setCountryName(rs.getString("country"));
                countriesList.add(country);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return countriesList;
    }

    /**
     *
     * @param name country name
     * @return object "country" from database
     */
    @Override
    public Country findEntityByName(String name) {
        Country country = new Country();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_COUNTRY_BY_NAME);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                country.setId(rs.getLong("id_country"));
                country.setCountryName(rs.getString("country"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return country;
    }

    /**
     * finds all countries in table "country" in the db
     * @return list with all countries
     */
    @Override
    public List<Country> findAll() {
        List<Country> countriesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        Statement st = null;
        try  {
            connection = connectionPool.takeConnection();
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_COUNTRIES);
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id_country"));
                country.setCountryName(rs.getString("country"));
                countriesList.add(country);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return countriesList;
    }


}
