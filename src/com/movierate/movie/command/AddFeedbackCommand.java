package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.entity.User;
import com.movierate.movie.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;

/**
 * command that picks the data entered by user to add a new feedback to a movie
 */
public class AddFeedbackCommand implements ICommand {

    private static final String FEEDBACK_TEXT = "feedback";
    private static final String ID_MOVIE = "id_movie";
    private static final String USER = "user";
    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(FEEDBACK_TEXT);
        long id_movie = Long.parseLong(request.getParameter(ID_MOVIE));

        //extract id_user from session!!!
        //User user = (User) request.getSession().getAttribute(USER);
        User user = new User();
        user.setId(30);
        FeedbackService feedbackService = new FeedbackService();
        if (feedbackService.createFeedback(user, id_movie, text)){
            return PagePath.MAIN_PAGE;
        } else {
            return PagePath.ERROR_PAGE;
        }
    }
}
