package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.FeedbackService;
import com.chatovich.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * gets all the info about the feedback using its id
 */
public class GetFeedbackCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetFeedbackCommand.class);
    @Override
    public String execute(HttpServletRequest request) {

        String id = request.getParameter(Parameters.ID_FEEDBACK);
        FeedbackService feedbackService = new FeedbackService();
        try {
            Feedback feedback = feedbackService.getFeedback(id);
            if (feedback.getId()>0){
                request.setAttribute(Parameters.FEEDBACK, feedback);
                request.setAttribute(Parameters.SHOW_FEEDBACK, true);
            } else {
                request.setAttribute(Parameters.NO_FEEDBACK, true);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.ADMIN_NEW_FEEDBACK_PAGE;
    }
}
