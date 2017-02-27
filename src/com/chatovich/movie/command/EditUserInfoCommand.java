package com.chatovich.movie.command;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.exception.HashPasswordFailedException;
import com.chatovich.movie.exception.ServiceException;
import com.chatovich.movie.service.IUserService;
import com.chatovich.movie.service.ServiceFactory;
import com.chatovich.movie.service.impl.UserServiceImpl;
import com.chatovich.movie.util.QueryUtil;
import com.chatovich.movie.util.Validation;
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
        IUserService userServiceImpl = ServiceFactory.getInstance().getUserService();
        try {
            if (!Validation.loginInfoValid(userServiceImpl.getLoginInfo(parameters),parameters)){
                request.setAttribute(Parameters.LOGIN_FAILED, true);
                return PagePath.USER_PAGE;
            }
            //get uploaded photo if there was one
            String path = uploadFile(request, Parameters.PHOTO_FILE_PATH, Parameters.PHOTO);
            userServiceImpl.updateUser(parameters, path);
            User user = userServiceImpl.getUser(parameters.get(Parameters.LOGIN)[0]);
            request.setAttribute(Parameters.USER_UPDATED, true);
            request.getSession().setAttribute(Parameters.SIGNED_USER, user);

        } catch (ServiceException|HashPasswordFailedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            return PagePath.ERROR_PAGE;
        }
        return PagePath.USER_PAGE;
    }
}
