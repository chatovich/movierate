package com.movierate.movie.tag;

import com.movierate.movie.constant.PagePath;
import com.movierate.movie.constant.Parameters;
import com.movierate.movie.type.Role;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Yultos_ on 08.12.2016
 */
@SuppressWarnings("serial")
public class LoginTag extends TagSupport {

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        if (Role.USER.getRoleName().equalsIgnoreCase(role)) {
            pageContext.setAttribute(Parameters.INCLUDE_MENU, PagePath.USER_MENU_PAGE);
        } else {
            pageContext.setAttribute(Parameters.INCLUDE_MENU,PagePath.ADMIN_MENU_PAGE);
        }
        return SKIP_BODY;
    }
}
