package com.movierate.movie.dao.impl;

import com.movierate.movie.connection.ConnectionPool;
import com.movierate.movie.connection.ProxyConnection;
import com.movierate.movie.dao.DAO;
import com.movierate.movie.dao.ParticipantDAO;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.type.Profession;
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
 * Class that connects with database and operates with table "movies"
 */
public class ParticipantDAOImpl implements ParticipantDAO, DAO {

    public static final Logger LOGGER = LogManager.getLogger(ParticipantDAOImpl.class);
    public static final String SQL_FIND_ALL_PARTICIPANTS = "SELECT id_participant, name, profession FROM participants";
    public static final String SQL_SAVE_PARTICIPANT = "INSERT INTO participants (name, profession) VALUES (?,?)";
    public static final String SQL_FIND_PARTICIPANTS_BY_PROFESSION = "SELECT id_participant, name, profession FROM participants WHERE profession=?";
    public static final String SQL_FIND_PARTICIPANTS_OF_MOVIE = "SELECT * FROM participants WHERE id_participant IN " +
            "(SELECT id_participant FROM movies_participants WHERE id_movie=?)";
    public static final String SQL_FIND_PARTICIPANT_BY_NAME = "SELECT id_participant, name, profession FROM participants WHERE name=?";


    /**
     *
     * @param id id of the movie which participants we need to find
     * @return list of all participants of the movie
     */
    public List<Participant> findParticipantsByMovieId(int id) {

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
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return participantsList;
    }


    @Override
    public List findEntityById(int id) {
        return null;
    }

    @Override
    public List<Participant> findEntityByName(String name) {
        List<Participant> participants = new ArrayList<>();
        Participant participant = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_FIND_PARTICIPANT_BY_NAME);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                participant = new Participant();
                participant.setId(rs.getLong("id_participant"));
                participant.setName(rs.getString("name"));
                participant.setProfession(Profession.valueOf(rs.getString("profession").toUpperCase()));
                participants.add(participant);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
        return participants;
    }


    @Override
    public List<Participant> findAllByProfession(String profession) {
        List<Participant> participants = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            if (!profession.isEmpty()){
                preparedStatement = connection.prepareStatement(SQL_FIND_PARTICIPANTS_BY_PROFESSION);
                preparedStatement.setString(1, profession);
                rs = preparedStatement.executeQuery();
            } else {
                statement = connection.createStatement();
                rs = statement.executeQuery(SQL_FIND_ALL_PARTICIPANTS);
            }
            while (rs.next()){
                Participant participant = new Participant();
                participant.setId(rs.getLong("id_participant"));
                participant.setName(rs.getString("name"));
                participant.setProfession(Profession.valueOf(rs.getString("profession").toUpperCase()));
                participants.add(participant);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
        } finally {
            close(statement);
            close(preparedStatement);
            connectionPool.releaseConnection(connection);
        }
        return participants;
    }

    @Override
    public void save(Participant participant) throws DAOFailedException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            st = connection.prepareStatement(SQL_SAVE_PARTICIPANT);
            st.setString(1, participant.getName());
            st.setString(2, String.valueOf(participant.getProfession()).toLowerCase());
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Problem connecting with db "+e.getMessage());
            throw new DAOFailedException("Saving participant failed: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
    }
}
