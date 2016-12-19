package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.entity.Participant;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.util.QueryUtil;
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
            request.setAttribute("chooseParticipant", true);
            request.setAttribute("participants", participants);
        } catch (DAOFailedException e) {
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
