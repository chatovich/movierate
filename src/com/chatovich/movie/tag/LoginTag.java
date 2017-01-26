package com.chatovich.movie.tag;

import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * adds the name of the user to welcome line on main page
 */
@SuppressWarnings("serial")
public class LoginTag extends TagSupport{

    @Override
    public int doStartTag() throws JspException {
        try {
            String line = "";
            if (pageContext.getSession().getAttribute(Parameters.USER_SIGNED_IN)!=null) {
                User user = (User) pageContext.getSession().getAttribute(Parameters.SIGNED_USER);
                line = ", "+user.getLogin();
            }
            pageContext.getOut().write(line);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
