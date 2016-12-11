package com.movierate.movie.tag;

import com.movierate.movie.constant.Parameters;
import com.movierate.movie.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * changes
 */
@SuppressWarnings("serial")
public class LoginTag extends TagSupport{

    @Override
    public int doStartTag() throws JspException {
        try {
            String line = "";
            if (pageContext.getSession().getAttribute(Parameters.ATTR_USER_SIGNED_IN)!=null) {
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
