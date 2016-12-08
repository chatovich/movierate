package com.movierate.movie.service;

import com.movierate.movie.dao.impl.ParticipantDAOImpl;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.type.Profession;

import java.util.List;
import java.util.Map;

/**
 * Class that encapsulates logic connected with entity "participant" and represents intermediate layer between database and client
 */
public class ParticipantService {

    public List<Participant> getParticipants (String profession) throws DAOFailedException {
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        return participantDAO.findAllByProfession(profession);
    }

    /**
     * creates a new object "participant" using parameters
     * @param parameters map with parameters' names and their values entered by admin
     * @throws DAOFailedException if there is a problem connecting with db and SQLException is thrown
     */
    public void createParticipant (Map<String, String[]> parameters) throws DAOFailedException {
        Participant participant = new Participant();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            switch (entry.getKey()){
                case "id_participant": participant.setId(Long.parseLong(entry.getValue()[0]));
                case "name": participant.setName(entry.getValue()[0]);
                    break;
                case "profession": participant.setProfession(Profession.valueOf(entry.getValue()[0].toUpperCase()));
                    break;
            }
        }
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        participantDAO.save(participant);
    }

    public boolean checkParticipantExists(Map<String, String[]> parameters){

        boolean participantExists = false;
        String name = "";
        String profession = "";
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            switch (entry.getKey()){
                case "name": name = entry.getValue()[0];
                    break;
                case "profession": profession = entry.getValue()[0].toUpperCase();
                    break;
            }
        }
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        List<Participant>participants = participantDAO.findEntityByName(name);
        for (Participant participant : participants) {
            if (String.valueOf(participant.getProfession()).toLowerCase().equals(profession.toLowerCase())){
                participantExists = true;
            }
        }
        return participantExists;
    }

    public Participant getParticipantById(long id) throws DAOFailedException {

        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        return participantDAO.findEntityById(id);
    }

}
