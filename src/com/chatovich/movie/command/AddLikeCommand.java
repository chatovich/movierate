package com.chatovich.movie.command;

import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.FeedbackServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * adds like to a feedback and returns  likes' quantity
 */
public class AddLikeCommand implements ICommand{

    private static final Logger LOGGER = LogManager.getLogger(AddLikeCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String id_user = request.getParameter(Parameters.ID_USE);
        String id_feedback = request.getParameter(Parameters.ID_FEEDBACK);
        String likes = request.getParameter(Parameters.LIKES);
        int likesCount=0;
        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        try {
            likesCount = feedbackServiceImpl.addLike(Long.parseLong(id_user),Long.parseLong(id_feedback),Integer.parseInt(likes));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return String.valueOf(likesCount);
    }
}
