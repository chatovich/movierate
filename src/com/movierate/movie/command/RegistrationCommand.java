package com.movierate.movie.command;

import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yultos_ on 26.11.2016
 */
public class RegistrationCommand implements ICommand {

    final static Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        List<String> wrongParameters = Validation.checkRegistFormByPattern(parameters);

        if (!wrongParameters.isEmpty()){
            request.setAttribute("registrFailed", true);
            for (int i = 0; i < wrongParameters.size(); i++) {
                request.setAttribute(wrongParameters.get(i)+"Wrong", wrongParameters.get(i));
            }
            return "jsp/login/reg.jsp";
        }
        if (!Validation.checkPasswordConfirm(parameters)){
            request.setAttribute("passwordsNoMatch", true);
            return "jsp/login/reg.jsp";
        }

        //get uploaded photo if there was one
        String path = "img/photo";
        Part filePart;
        String fileName;
        String filePath;
        try {
            filePart = request.getPart("photo");
            if (filePart!=null){
                fileName = Validation.getFileName(filePart);
                filePath = request.getServletContext().getRealPath("/") + File.separator + path + File.separator + fileName;
                filePart.write(filePath);
            }
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "I/O problem with loading file "+e.getMessage());
        } catch (ServletException e) {
            LOGGER.log(Level.ERROR, "Servlet problem "+e.getMessage());
        }
        request.setAttribute("registrFailed", false);
        return "jsp/main/main.jsp";

    }
}
