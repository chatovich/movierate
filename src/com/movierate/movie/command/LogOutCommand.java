package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * invalidates the session when user wants to log out
 */
public class LogOutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().invalidate();
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.MAIN_PAGE;
    }
}
