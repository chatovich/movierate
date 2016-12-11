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
 * tag that switches menu user-admin depending on role
 */
@SuppressWarnings("serial")
public class SwitchMenuTag extends TagSupport {

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

