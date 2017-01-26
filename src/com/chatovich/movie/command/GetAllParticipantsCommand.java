package com.chatovich.movie.command;

import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.ParticipantService;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Participant;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * command that gets all participants from the db
 */
public class GetAllParticipantsCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(GetAllParticipantsCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        ParticipantService participantService = new ParticipantService();
        List<Participant> participants;
        try {
            participants = participantService.getParticipants("");
            request.setAttribute(Parameters.CHOOSE_PARTICIPANT, true);
            request.setAttribute(Parameters.PARTICIPANTS, participants);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
