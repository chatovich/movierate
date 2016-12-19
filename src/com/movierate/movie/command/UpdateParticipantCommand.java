package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.util.QueryUtil;
import com.movierate.movie.util.Validation;
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
            request.setAttribute("emptyField", true);
            return PagePath.USER_PAGE;
        }
        ParticipantService participantService = new ParticipantService();
        try {
            participantService.createParticipant(parameters);
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, e);
        }
        request.setAttribute("participantUpdated", true);
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
