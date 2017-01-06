package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.Feedback;
import com.movierate.movie.exception.DAOFailedException;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.FeedbackService;
import com.movierate.movie.type.FeedbackStatus;
import com.movierate.movie.util.QueryUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * command that gets all new comments (not published yet) from the db
 */
public class GetNewFeedbacksCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(GetNewFeedbacksCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        FeedbackService feedbackService = new FeedbackService();
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbacksByStatus(FeedbackStatus.NEW.getStatus());
            request.setAttribute(Parameters.NEW_FEEDBACKS, feedbacks);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.ADMIN_NEW_FEEDBACK_PAGE;
    }
}
