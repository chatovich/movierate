package com.movierate.movie.controller;

import com.movierate.movie.command.CommandType;
import com.movierate.movie.command.ICommand;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.util.QueryUtil;

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
        if ("add_like".equalsIgnoreCase(commandName)){
            System.out.println(pageName);
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            response.getWriter().write(pageName);
        } else{
        if (commandName.equalsIgnoreCase("change_language")&&request.getSession(true).getAttribute("prev")!=null){
            response.sendRedirect((String)request.getSession(true).getAttribute("prev"));
        } else
//        response.sendRedirect(QueryUtil.createHttpQueryString(request));
        request.getRequestDispatcher(pageName).forward(request, response);}
    }

}
