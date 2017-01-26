package com.chatovich.movie.controller;

import com.chatovich.movie.command.CommandType;
import com.chatovich.movie.command.ICommand;
import com.chatovich.movie.constant.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Servlet for processing ajax
 */
@MultipartConfig
@WebServlet(name = "controllerAjax", urlPatterns = {"/controllerajax"})
public class ControllerAjax extends HttpServlet{

    private static final long serialVersionUID = 1L;

    public ControllerAjax() {
        super();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);
    }

    protected void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        ICommand command  = commandType.getCommand();
        String pageName = command.execute(request);
//        if (Parameters.ADD_LIKE.equalsIgnoreCase(commandName)){
            response.setContentType("text/plain");
//            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(pageName);
//        } else{
//            request.getRequestDispatcher(pageName).forward(request, response);}
    }

}
