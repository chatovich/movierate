package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
