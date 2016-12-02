package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Yultos_ on 20.11.2016
 */
public class ChangeLanguageCommand implements ICommand {

    private static final String PARAM_LANGUAGE = "language";

    @Override
    public String execute(HttpServletRequest request)  {
        HttpSession session = request.getSession(true);
        String local = request.getParameter(PARAM_LANGUAGE);
        session.setAttribute(PARAM_LANGUAGE, local);
        return PagePath.MAIN_PAGE;
    }
}
