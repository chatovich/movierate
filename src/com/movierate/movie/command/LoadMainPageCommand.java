package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.util.QueryUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * gets all necessary data to load main page
 */
public class LoadMainPageCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("prev", QueryUtil.createHttpQueryString(request));
        return PagePath.MAIN_PAGE;
    }
}
