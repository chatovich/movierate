package com.chatovich.movie.tag;

import com.chatovich.movie.constant.PagePath;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.type.Role;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

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
        if (Role.ADMIN.getRoleName().equalsIgnoreCase(role)) {
            pageContext.setAttribute(Parameters.INCLUDE_MENU, PagePath.ADMIN_MENU_PAGE);
        } else {
            pageContext.setAttribute(Parameters.INCLUDE_MENU,PagePath.USER_MENU_PAGE);
        }
        return SKIP_BODY;
    }
}

