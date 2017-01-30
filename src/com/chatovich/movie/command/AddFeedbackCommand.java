package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.Movie;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IFeedbackService;
import com.chatovich.movie.service.IMovieService;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.UserServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.service.impl.FeedbackServiceImpl;
import com.chatovich.movie.service.impl.MovieServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * command that picks the data entered by user to add a new feedback to a movie
 */
public class AddFeedbackCommand implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(AddFeedbackCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(Parameters.FEEDBACK);
        long id_movie = Long.parseLong(request.getParameter(Parameters.ID_MOVIE));
        int mark = Integer.parseInt(request.getParameter(Parameters.MOVIE_MARK));
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(Parameters.SIGNED_USER);
        IFeedbackService feedbackServiceImpl = ServiceFactory.getInstance().getFeedbackService();
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();
        IMovieService movieServiceImpl = ServiceFactory.getInstance().getMovieService();
        Movie movie;
        try {
            feedbackServiceImpl.createFeedback(user, id_movie, text, mark);
            movie = movieServiceImpl.findMovieById(id_movie);
            request.setAttribute(Parameters.MOVIE, movie);
            request.setAttribute(Parameters.FEEDBACK_ADDED, true);
            session.setAttribute(Parameters.SIGNED_USER, userServiceImpl.updateUserFeedbacks(user));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MOVIE_PAGE;
    }
}
