package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.ParticipantService;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * command that gets the info about the participant admin wanna update
 */
public class GetParticipantForUpdateCommand implements ICommand{

    private static final Logger LOGGER = LogManager.getLogger(GetParticipantForUpdateCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        Long id = Long.parseLong(request.getParameter(Parameters.ID_PARTICIPANT));
        ParticipantService participantService = new ParticipantService();
        Participant participant;
        try {
            participant = participantService.getParticipantById(id);
            request.setAttribute(Parameters.PARTICIPANT, participant);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.UPDATE_PARTICIPANT_PAGE;
    }
}
