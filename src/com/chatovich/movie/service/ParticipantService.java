package com.chatovich.movie.service;

import com.chatovich.movie.dao.impl.ParticipantDAOImpl;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.type.Profession;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.exception.DAOFailedException;

import java.util.List;
import java.util.Map;

/**
 * Class that encapsulates logic connected with entity "participant" and represents intermediate layer between database and client
 */
public class ParticipantService {

    /**
     * gets all participants of specified profession
     * @param profession actor or director, or empty line if all participants needed
     * @return list of participants
     * @throws ServiceException if DAOFailedException is thrown
     */
    public List<Participant> getParticipants (String profession) throws ServiceException {
        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        List<Participant> participants;
        try {
            participants = participantDAO.findAllByProfession(profession);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return  participants;
    }

    /**
     * creates a new object "participant" using parameters
     * @param parameters map with parameters' names and their values entered by admin
     * @throws ServiceException if there is a problem connecting with db and DAOFailedException is thrown
     */
    public void createParticipant (Map<String, String[]> parameters) throws ServiceException {
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
        try {
            participantDAO.save(participant);
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * checks whether such participant already exists
     * @param parameters name and profession entered by admin
     * @return true if exists, otherwise - false
     * @throws ServiceException if DAOFailedException is thrown
     */
    public boolean checkParticipantExists(Map<String, String[]> parameters) throws ServiceException {

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
        List<Participant> participants;
        try {
            participants = participantDAO.findEntityByName(name);
            if (!participants.isEmpty()) {
                for (Participant participant : participants) {
                    if (String.valueOf(participant.getProfession()).toLowerCase().equals(profession.toLowerCase())){
                        participantExists = true;
                    }
                }
            }
        } catch (DAOFailedException e) {
            throw new ServiceException(e.getMessage());
        }
        return participantExists;
    }

    /**
     * gets participant by id
     * @param id participant id
     * @return participant
     * @throws ServiceException if DAOFailedException is thrown
     */
    public Participant getParticipantById(long id) throws ServiceException {

        ParticipantDAOImpl participantDAO = new ParticipantDAOImpl();
        Participant participant;
        try {
            participant = participantDAO.findEntityById(id);
        } catch (DAOFailedException e) {
            throw new ServiceException(e);
        }
        return participant;
    }

}
