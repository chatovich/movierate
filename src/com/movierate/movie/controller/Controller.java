package com.movierate.movie.controller;

import com.movierate.movie.command.CommandType;
import com.movierate.movie.command.ICommand;
import com.movierate.movie.constant.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yultos_ on 20.11.2016
 */
@MultipartConfig
@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet{

    private static final long serialVersionUID = 1L;

    public Controller() {
        super();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);
    }

    protected void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        ICommand command  = commandType.getCommand();
        String pageName = command.execute(request);
        request.getRequestDispatcher(pageName).forward(request, response);
    }

}
