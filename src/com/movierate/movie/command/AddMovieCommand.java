package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.service.MovieService;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * command that picks the data entered by admin to add a new movie on the website
 */
public class AddMovieCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(AddMovieCommand.class);
    private static final String FILE_PATH = "/img/poster";
    private static final String PARAM_POSTER = "poster";

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        //get uploaded photo if there was one
        String path = FILE_PATH;
        Part filePart;
        String fileName;
        String filePath = null;
        try {
            filePart = request.getPart(PARAM_POSTER);
            if (filePart.getSize()>0){
                fileName = Validation.getFileName(filePart);
                //writes to out folder!!!! works!
                filePath = request.getServletContext().getRealPath("") + File.separator + path + File.separator + fileName;
                filePart.write(filePath);
                path= path+"/"+fileName;
            } else {
                path = null;
            }
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "I/O problem with loading file "+e.getMessage());
        } catch (ServletException e) {
            LOGGER.log(Level.ERROR, "Servlet problem "+e.getMessage());
        }

        MovieService movieService = new MovieService();
        movieService.createMovie(parameters,path);
        return PagePath.MAIN_PAGE;
    }
}
