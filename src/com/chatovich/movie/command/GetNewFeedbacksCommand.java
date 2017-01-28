package com.chatovich.movie.command;

import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Feedback;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.FeedbackServiceImpl;
import com.chatovich.movie.type.FeedbackStatus;
import com.chatovich.movie.util.QueryUtil;
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

        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        try {
            List<Feedback> feedbacks = feedbackServiceImpl.getFeedbacksByStatus(FeedbackStatus.NEW.getStatus());
            request.setAttribute(Parameters.NEW_FEEDBACKS, feedbacks);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.ADMIN_NEW_FEEDBACK_PAGE;
    }
}
