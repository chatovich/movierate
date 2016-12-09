package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * invalidates the session when user wants to log out
 */
public class LogOutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().invalidate();
        return PagePath.MAIN_PAGE;
    }
}
