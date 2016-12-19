package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * command that gets the info about the participant admin wanna update
 */
public class GetParticipantForUpdateCommand implements ICommand{
    @Override
    public String execute(HttpServletRequest request) {

        Long id = Long.parseLong(request.getParameter("id_participant"));
        ParticipantService participantService = new ParticipantService();
        Participant participant;
        try {
            participant = participantService.getParticipantById(id);
            request.setAttribute("participant", participant);
        } catch (DAOFailedException e) {
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.UPDATE_PARTICIPANT_PAGE;
    }
}
