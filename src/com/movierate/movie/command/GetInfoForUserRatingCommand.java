package com.movierate.movie.command;

import javax.servlet.http.HttpServletRequest;

/**
 * gets movies which were commented by signed user
 */
public class GetInfoForUserRatingCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
