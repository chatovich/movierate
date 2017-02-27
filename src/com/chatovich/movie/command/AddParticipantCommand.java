package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IParticipantService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.ParticipantServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * command that picks the data entered by admin to add a new movie participant into the db
 */
public class AddParticipantCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(AddParticipantCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        IParticipantService participantServiceImpl = ServiceFactory.getInstance().getParticipantService();
        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute(Parameters.EMPTY_FIELDS, true);
            return PagePath.USER_PAGE;
        }
        try{
            if (participantServiceImpl.checkParticipantExists(parameters)){
                request.setAttribute(Parameters.PARTICIPANT_EXISTS,true);
                return PagePath.USER_PAGE;
            }
            participantServiceImpl.createParticipant(parameters);
            request.setAttribute(Parameters.PARTICIPANT_ADDED, true);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Participant adding failed: "+e.getMessage());
            request.setAttribute(Parameters.PARTICIPANT_ADDED, false);
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_PAGE;
    }
}
