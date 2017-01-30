package com.chatovich.movie.controller;

import com.chatovich.movie.command.CommandHelper;
import com.chatovich.movie.command.CommandType;
import com.chatovich.movie.command.ICommand;
import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger(ControllerAjax.class);

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
        ICommand command = CommandHelper.getInstance().getCommand(commandName);
        String pageName;
        if (command!=null){
            pageName = command.execute(request);
            response.setContentType("text/plain");
            response.getWriter().write(pageName);
        } else {
            LOGGER.log(Level.ERROR, "No such command");
            request.getRequestDispatcher(PagePath.ERROR_PAGE).forward(request,response);
        }
    }

}
