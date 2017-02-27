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
 * command that picks the data entered by admin to edit participant info
 */
public class UpdateParticipantCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(UpdateParticipantCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute(Parameters.EMPTY_FIELDS, true);
            return PagePath.USER_PAGE;
        }
        IParticipantService participantServiceImpl = ServiceFactory.getInstance().getParticipantService();
        try {
            participantServiceImpl.createParticipant(parameters);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e);
            return PagePath.ERROR_PAGE;
        }
        request.setAttribute(Parameters.PARTICIPANT_UPDATED, true);
        return PagePath.USER_PAGE;
    }
}
