package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.FeedbackService;
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
        FeedbackService feedbackService = new FeedbackService();
        try {
            likesCount = feedbackService.addLike(Long.parseLong(id_user),Long.parseLong(id_feedback),Integer.parseInt(likes));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return String.valueOf(likesCount);
    }
}
