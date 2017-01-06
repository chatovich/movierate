package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * command that gets the info about the participant admin wanna update
 */
public class GetParticipantForUpdateCommand implements ICommand{
    @Override
    public String execute(HttpServletRequest request) {

        Long id = Long.parseLong(request.getParameter(Parameters.ID_PARTICIPANT));
        ParticipantService participantService = new ParticipantService();
        Participant participant;
        try {
            participant = participantService.getParticipantById(id);
            request.setAttribute(Parameters.PARTICIPANT, participant);
        } catch (ServiceException e) {
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.UPDATE_PARTICIPANT_PAGE;
    }
}
