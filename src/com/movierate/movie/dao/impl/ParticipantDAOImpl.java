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

    private static final String SQL_FIND_ALL_PARTICIPANTS = "SELECT id_participant, name, profession FROM participants";
    private static final String SQL_SAVE_PARTICIPANT = "INSERT INTO participants (name, profession) VALUES (?,?)";
    private static final String SQL_UPDATE_PARTICIPANT = "UPDATE participants SET name=?, profession=? WHERE id_participant=?";
    private static final String SQL_FIND_PARTICIPANTS_BY_PROFESSION = "SELECT id_participant, name, profession FROM participants WHERE profession=?";
    private static final String SQL_FIND_PARTICIPANTS_OF_MOVIE = "SELECT * FROM participants WHERE id_participant IN " +
            "(SELECT id_participant FROM movies_participants WHERE id_movie=?)";
    private static final String SQL_FIND_PARTICIPANT_BY_NAME = "SELECT id_participant, name, profession FROM participants WHERE name=?";
    private static final String SQL_FIND_PARTICIPANT_BY_ID = "SELECT id_participant, name, profession FROM participants WHERE id_participant=?";


    /**
     *
     * @param id id of the movie which participants we need to find
     * @return list of all participants of the movie
     */
    @Override
    public List<Participant> findParticipantsByMovieId(int id) throws DAOFailedException {

        List<Participant> participantsList = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try  (
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_PARTICIPANTS_OF_MOVIE)){
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
            throw new DAOFailedException("Impossible to find participants by movie id: "+e.getMessage());
        }
        return participantsList;
    }

    /**
     * finds participant in the db using its name
     * @param name participant name
     * @return list of participants
     */
    @Override
    public List<Participant> findEntityByName(String name) throws DAOFailedException {
        List<Participant> participants = new ArrayList<>();
        Participant participant = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try(
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_PARTICIPANT_BY_NAME)){
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
            throw new DAOFailedException("Impossible to find participant by its name: "+e.getMessage());
        }
        return participants;
    }


    /**
     * finds all participants or those who profession is specified
     * @param profession actor or director, or empty if all participants are needed
     * @return list of participants
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public List<Participant> findAllByProfession(String profession) throws DAOFailedException {
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
            throw new DAOFailedException("Impossible to get participants from the db: "+e.getMessage());
        } finally {
            close(statement);
            close(preparedStatement);
            connectionPool.releaseConnection(connection);
        }
        return participants;
    }

    /**
     * saves new paricipant into the db
     * @param participant new participant
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public void save(Participant participant) throws DAOFailedException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        PreparedStatement st = null;
        try{
            connection = connectionPool.takeConnection();
            if (participant.getId()==0){
                st = connection.prepareStatement(SQL_SAVE_PARTICIPANT);
            } else {
                st = connection.prepareStatement(SQL_UPDATE_PARTICIPANT);
                st.setLong(3, participant.getId());
            }

            st.setString(1, participant.getName());
            st.setString(2, String.valueOf(participant.getProfession()).toLowerCase());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOFailedException("Saving/updationg participant failed: "+e.getMessage());
        } finally {
            close(st);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * finds participant in the db using its id
     * @param id participant id
     * @return participant
     * @throws DAOFailedException if SQLException is thrown
     */
    @Override
    public Participant findEntityById(long id) throws DAOFailedException {

        Participant participant = null;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try(
            ProxyConnection connection = connectionPool.takeConnection();
            PreparedStatement st = connection.prepareStatement(SQL_FIND_PARTICIPANT_BY_ID)){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                participant = new Participant();
                participant.setId(rs.getLong("id_participant"));
                participant.setName(rs.getString("name"));
                participant.setProfession(Profession.valueOf(rs.getString("profession").toUpperCase()));
            }
        } catch (SQLException e) {
            throw new DAOFailedException("Impossible to find the participant by id: "+e.getMessage());
        }
        return participant;
    }
}
