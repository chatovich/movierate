package com.movierate.movie.command;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.User;
import com.movierate.movie.exception.ServiceException;
import com.movierate.movie.service.UserService;
import com.movierate.movie.util.QueryUtil;
import com.movierate.movie.util.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * picks the data entered by user to edit contact info (email, password, photo)
 */
public class EditUserInfoCommand extends UploadPhoto implements ICommand {

    public static final Logger LOGGER = LogManager.getLogger(EditUserInfoCommand.class);
    @Override
    public String execute(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();
        List<String> emptyFields = Validation.checkEmptyFields(parameters);
        emptyFields.remove(Parameters.NEW_PASSWORD);
        emptyFields.remove(Parameters.PASSWORD_CONFIRM);
        if (!emptyFields.isEmpty()){
            request.setAttribute(Parameters.EMPTY_FIELDS, true);
            return PagePath.USER_PAGE;
        }
        UserService userService = new UserService();
        try {
            if (!Validation.loginInfoValid(userService.getLoginInfo(parameters),parameters)){
                request.setAttribute(Parameters.EMPTY_FIELDS, true);
                return PagePath.USER_PAGE;
            }
            //get uploaded photo if there was one
            String path = uploadFile(request, Parameters.PHOTO_FILE_PATH, Parameters.PHOTO);
            userService.updateUser(parameters, path);
            User user = userService.getUser(parameters.get(Parameters.LOGIN)[0]);
            request.setAttribute(Parameters.USER_UPDATED, true);
            request.getSession().setAttribute(Parameters.SIGNED_USER, user);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        request.getSession(true).setAttribute(Parameters.PREVIOUS_PAGE, QueryUtil.createHttpQueryString(request));
        return PagePath.USER_PAGE;
    }
}
