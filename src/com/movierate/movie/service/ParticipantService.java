package com.movierate.movie.service;

import com.movierate.movie.dao.impl.ParticipantDAOImpl;
import com.movierate.movie.entity.Participant;

import java.util.List;

/**
 * Class that encapsulates logic connected with entity "participant" and represents intermediate layer between database and client
 */
public class ParticipantService {

    public List<Participant> getParticipants (String profession){
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        return participantDAO.findAllByProfession(profession);
    }
}
