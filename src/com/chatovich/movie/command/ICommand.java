package com.chatovich.movie.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yultos_ on 20.11.2016
 */
public interface ICommand {

    public String execute (HttpServletRequest request) ;
}
