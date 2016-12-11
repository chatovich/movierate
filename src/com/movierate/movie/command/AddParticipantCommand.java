package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.ParticipantService;
import com.movierate.movie.util.Validation;
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

        ParticipantService participantService = new ParticipantService();
        Map<String, String[]> parameters = request.getParameterMap();
        if (!Validation.checkEmptyFields(parameters).isEmpty()){
            request.setAttribute("emptyFields", true);
            return PagePath.USER_PAGE;
        }
        if (participantService.checkParticipantExists(parameters)){
            request.setAttribute("participantExists",true);
            return PagePath.USER_PAGE;
        }

        try{
            participantService.createParticipant(parameters);
            request.setAttribute("participantAdded", true);

        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, "Participant adding failed: "+e.getMessage());
            request.setAttribute("participantAdded", false);
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_PAGE;
    }
}
