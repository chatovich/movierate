package com.chatovich.movie.controller;

import com.chatovich.movie.command.ICommand;
import com.chatovich.movie.command.CommandType;
import com.chatovich.movie.constant.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to unite code and UI
 */
@MultipartConfig
@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Controller() {
        super();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        ICommand command = commandType.getCommand();
        String pageName = command.execute(request);
        if (commandName.equalsIgnoreCase(Parameters.CHANGE_LANGUAGE) && request.getSession(true).getAttribute(Parameters.PREVIOUS_PAGE) != null) {
            response.sendRedirect((String) request.getSession(true).getAttribute(Parameters.PREVIOUS_PAGE));
        } else {
            request.getRequestDispatcher(pageName).forward(request, response);
        }
    }
}


