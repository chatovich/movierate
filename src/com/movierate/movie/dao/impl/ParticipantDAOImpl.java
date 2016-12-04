package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAOI;
import com.movierate.movie.dao.ParticipantDAOI;
import com.movierate.movie.entity.Entity;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.type.Profession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that connects with database and operates with table "movies"
 */
public class ParticipantDAOImpl implements ParticipantDAOI, DAOI {

    public static final Logger LOGGER = LogManager.getLogger(ParticipantDAOImpl.class);
    public static final String SQL_FIND_PARTICIPANTS_OF_MOVIE = "SELECT * FROM participants WHERE id_participant IN " +
            "(SELECT id_participant FROM movies_participants WHERE id_movie=?)";


    @Override
    public List<Participant> findEntityById(int id) {

        List<Participant> participantsList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;

        try  {
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_PARTICIPANTS_OF_MOVIE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Participant participant = new Participant();
                participant.setId(rs.getInt("id_participant"));
                participant.setName(rs.getString("name"));
                participant.setProfession(Profession.valueOf(rs.getString("profession").toUpperCase()));
                participantsList.add(participant);
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
        return participantsList;
    }
}
