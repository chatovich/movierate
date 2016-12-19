package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.service.FeedbackService;
import com.movierate.movie.util.QueryUtil;
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

        String id = request.getParameter("id_feedback");
        FeedbackService feedbackService = new FeedbackService();
        try {
            Feedback feedback = feedbackService.getFeedback(id);
            if (feedback.getId()>0){
                request.setAttribute(Parameters.FEEDBACK, feedback);
                request.setAttribute(Parameters.SHOW_FEEDBACK, true);
            } else {
                request.setAttribute(Parameters.NO_FEEDBACK, true);
            }
        } catch (DAOFailedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.ADMIN_NEW_FEEDBACK_PAGE;
    }
}
