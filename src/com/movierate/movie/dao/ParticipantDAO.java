package com.movierate.movie.dao;

import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;

import java.util.List;

/**
 * specifies methods for working with entity "participant" that its implementations should realize
 */
public interface ParticipantDAO {

    List<Participant> findEntityByName (String name);
    List<Participant> findAllByProfession (String profession);
    void save(Participant participant) throws DAOFailedException;

}
