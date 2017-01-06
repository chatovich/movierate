package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Yultos_ on 20.11.2016
 */
public class ChangeLanguageCommand implements ICommand {


    @Override
    public String execute(HttpServletRequest request)  {
        HttpSession session = request.getSession(true);
        String local = request.getParameter(Parameters.LANGUAGE);
        session.setAttribute(Parameters.LANGUAGE, local);
        return PagePath.MAIN_PAGE;
    }
}
