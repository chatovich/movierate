package com.chatovich.movie.command;

import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IParticipantService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.ParticipantServiceImpl;
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
        IParticipantService participantServiceImpl = ServiceFactory.getInstance().getParticipantService();
        List<Participant> participants;
        try {
            participants = participantServiceImpl.getParticipants("");
            request.setAttribute(Parameters.CHOOSE_PARTICIPANT, true);
            request.setAttribute(Parameters.PARTICIPANTS, participants);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_PAGE;
    }
}
