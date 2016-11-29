package com.movierate.movie.dao;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.entity.Country;
import com.movierate.movie.entity.Entity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "countries"
 */
public class CountryDAO extends AbstractDAO{

    public static final Logger LOGGER = LogManager.getLogger(CountryDAO.class);
    public static final String SQL_FIND_COUNTRIES_OF_MOVIE = "SELECT * FROM countries WHERE id_country IN " +
            "(SELECT id_country FROM movies_countries WHERE id_movie=?)";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findEntityByName(String name) {
        return null;
    }

    /**
     * finds all countries of the movie by movie id
     * @param id id of the movie
     * @return list containing countries of the movie
     */
    @Override
    public List<Country> findEntityById(int id) {
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
            if (st!=null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
                }
            }
            connectionPool.releaseConnection(connection);
        }
        return countriesList;
    }

    @Override
    public boolean create(Entity entity) {
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }
}
