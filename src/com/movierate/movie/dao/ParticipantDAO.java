package com.movierate.movie.dao;

import com.movierate.movie.entity.Participant;

import java.util.List;

/**
 * specifies methods for working with entity "participant" that its implementations should realize
 */
public interface ParticipantDAO {

    Participant findEntityByName (String name);
    List<Participant> findAllByProfession (String profession);

}
