package com.chatovich.movie.dao;

import com.chatovich.movie.exception.DAOFailedException;
import com.chatovich.movie.entity.Participant;

import java.util.List;

/**
 * specifies methods for working with entity "participant" that its implementations should realize
 */
public interface ParticipantDAO {

    List<Participant> findParticipantsByMovieId(long id) throws DAOFailedException;
    List<Participant> findEntityByName (String name) throws DAOFailedException;
    List<Participant> findAllByProfession (String profession) throws DAOFailedException;
    void save(Participant participant) throws DAOFailedException;
    Participant findEntityById (long id) throws DAOFailedException;

}
