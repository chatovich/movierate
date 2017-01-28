package com.chatovich.movie.service;

import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * interface to work with entity "participant"
 */
public interface IParticipantService {

    List<Participant> getParticipants (String profession) throws ServiceException;
    void createParticipant (Map<String, String[]> parameters) throws ServiceException;
    boolean checkParticipantExists(Map<String, String[]> parameters) throws ServiceException;
    Participant getParticipantById(long id) throws ServiceException;
}
