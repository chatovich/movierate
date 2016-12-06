package com.movierate.movie.command;

import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * class that contains methods for uploading a file
 */
public abstract class UploadPhoto {

    private static final Logger LOGGER = LogManager.getLogger(UploadPhoto.class);

    public String uploadFile (HttpServletRequest request, String path, String parameter) {

        Part filePart;
        String fileName;
        String filePath = null;
        try {
            filePart = request.getPart(parameter);
            if (filePart.getSize() > 0) {
                fileName = getFileName(filePart);
                //writes to out folder!!!! works!
                filePath = request.getServletContext().getRealPath("") + File.separator + path + File.separator + fileName;
                filePart.write(filePath);
                path = path + "/" + fileName;
            } else {
                path = null;
            }
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "I/O problem with loading file " + e.getMessage());
        } catch (ServletException e) {
            LOGGER.log(Level.ERROR, "Servlet problem " + e.getMessage());
        }
        return path;
    }

    public static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
