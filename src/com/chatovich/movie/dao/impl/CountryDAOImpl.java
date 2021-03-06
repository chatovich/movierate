package com.chatovich.movie.dao.impl;

import com.chatovich.movie.connection.ConnectionPool;
import com.chatovich.movie.connection.ProxyConnection;
import com.chatovich.movie.dao.ICountryDAO;
import com.chatovich.movie.dao.DAO;
import com.chatovich.movie.entity.Country;
import com.chatovich.movie.exception.DAOFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "countries"
 */
public class CountryDAOImpl implements ICountryDAO, DAO {

    private static final String SQL_FIND_COUNTRIES_OF_MOVIE = "SELECT * FROM countries WHERE id_country IN " +
            "(SELECT id_country FROM movies_countries WHERE id_movie=?)";
    private static final String SQL_FIND_COUNTRY_BY_NAME = "SELECT id_country, country FROM countries WHERE country=?";
    private static final String SQL_FIND_ALL_COUNTRIES = "SELECT id_country, country FROM countries";

    /**
     * finds all countries of the movie by movie id
     * @param id id of the movie
     * @return list containing countries of the movie
     */
    @Override
    public List<Country> findCountriesByMovieId(long id) throws DAOFailedException {
        List<Country> countriesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try  (ProxyConnection connection = connectionPool.takeConnection();
              PreparedStatement st = connection.prepareStatement(SQL_FIND_COUNTRIES_OF_MOVIE)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id_country"));
                country.setCountryName(rs.getString("country"));
                countriesList.add(country);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find countries by movie id: "+e.getMessage());
        }
        return countriesList;
    }

    /**
     * finds info about country by its name
     * @param name country name
     * @return object "country" from database
     */
    @Override
    public Country findEntityByName(String name) throws DAOFailedException {
        Country country = new Country();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try(
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_COUNTRY_BY_NAME)){
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                country.setId(rs.getLong("id_country"));
                country.setCountryName(rs.getString("country"));
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find country: "+e.getMessage());
        }
        return country;
    }

    /**
     * finds all countries in table "country" in the db
     * @return list with all countries
     */
    @Override
    public List<Country> findAll() throws DAOFailedException {
        List<Country> countriesList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_COUNTRIES);
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id_country"));
                country.setCountryName(rs.getString("country"));
                countriesList.add(country);
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find countries: "+e.getMessage());
        }
        return countriesList;
    }
}
