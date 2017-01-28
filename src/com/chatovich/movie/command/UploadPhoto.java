package com.chatovich.movie.command;

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

    /**
     *
     * @param request HttpServletRequest request from jsp page where user can upload a foto
     * @param path directory where the file should be save
     * @param parameter value of the parameter from the form on jsp page (poster, photo)
     * @return path to the saved file if there was one otherwise null
     */
   String uploadFile (HttpServletRequest request, String path, String parameter) {

        Part filePart;
        String fileName;
        String filePath = null;
        try {
            filePart = request.getPart(parameter);
            if (filePart.getSize() > 0) {
                fileName = getFileName(filePart);
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

    private static String getFileName(final Part part) {

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
