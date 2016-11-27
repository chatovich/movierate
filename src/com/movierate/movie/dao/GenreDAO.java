package com.movierate.movie.dao;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "genres"
 */
public class GenreDAO extends AbstractDAO {

    public static final String SQL_FIND_GENRES_OF_MOVIE = "SELECT genre FROM genres WHERE id_genre IN " +
            "(SELECT id_genre FROM movies_genres WHERE id_movie=?)";

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findEntityByName(String name) {
        return null;
    }

    @Override
    public List findEntityById(String id) {
        List<Entity> genresList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (ProxyConnection connection = connectionPool.takeConnection();
             PreparedStatement st = connection.prepareStatement(SQL_FIND_GENRES_OF_MOVIE)){
            st.setString(1,id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genresList;
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
