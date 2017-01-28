package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.service.impl.FeedbackServiceImpl;
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
        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        try {
            feedbackServiceImpl.updateFeedbackStatus(isAccepted, id);
            if (isAccepted){
                request.setAttribute(Parameters.FEEDBACK_ACCEPTED, true);
            } else {
                request.setAttribute(Parameters.FEEDBACK_REJECTED, true);
            }

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.ADMIN_NEW_FEEDBACK_PAGE;
    }
}
