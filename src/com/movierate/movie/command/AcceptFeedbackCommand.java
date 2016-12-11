package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.FeedbackService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * command that picks the data entered by admin to accept or reject a new feedback to a movie
 */
public class AcceptFeedbackCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(AcceptFeedbackCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        boolean isAccepted = Boolean.parseBoolean(request.getParameter(Parameters.FEEDBACK_ACTION));
        String id = request.getParameter(Parameters.ID_FEEDBACK);
        FeedbackService feedbackService = new FeedbackService();
        try {
            feedbackService.updateFeedbackStatus(isAccepted, id);
            request.setAttribute(Parameters.STATUS_UPDATED, true);
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.ADMIN_NEW_FEEDBACK_PAGE;
    }
}
