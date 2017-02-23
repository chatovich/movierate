package com.chatovich.movie.filter;

import com.chatovich.movie.command.CommandHelper;
import com.chatovich.movie.command.CommandType;
import com.chatovich.movie.constant.Parameters;
import com.chatovich.movie.entity.User;
import com.chatovich.movie.type.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


/**
 * checks the access to admin commands
 */
@WebFilter(filterName = "AdminCommandFilter",urlPatterns = {"/jsp/*"},
        initParams = {
                @WebInitParam(name = "INDEX_PATH", value = "index.jsp")})

public class AdminCommandFilter implements Filter {

    public static final Logger LOGGER = LogManager.getLogger(AdminCommandFilter.class);
    private String indexPath;
    private ArrayList<CommandType> adminCommands;

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
        adminCommands = new ArrayList<>();
        adminCommands.add(CommandType.ACCEPT_FEEDBACK);
        adminCommands.add(CommandType.ADD_MOVIE);
        adminCommands.add(CommandType.ADD_PARTICIPANT);
        adminCommands.add(CommandType.CHANGE_USER_STATUS);
        adminCommands.add(CommandType.GET_ALL_PARTICIPANTS);
        adminCommands.add(CommandType.GET_ALL_USERS);
        adminCommands.add(CommandType.GET_INFO_FOR_MOVIE_ADDING);
        adminCommands.add(CommandType.GET_PARTICIPANT_FOR_UPDATE);
        adminCommands.add(CommandType.UPDATE_MOVIE);
        adminCommands.add(CommandType.UPDATE_PARTICIPANT);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String command = request.getParameter(Parameters.COMMAND);
        if(command == null || command.isEmpty()){
            filterChain.doFilter(servletRequest, servletResponse);
        } else{
            CommandType commandName  = CommandHelper.getInstance().getCommandName(command);
            if(adminCommands.contains(commandName)){
                HttpSession session = request.getSession(false);
                if (session != null){
                    User signedUser = (User)session.getAttribute(Parameters.SIGNED_USER);
                    if (signedUser != null) {
                        if (signedUser.getRole().equals(Role.ADMIN)) {
                            filterChain.doFilter(servletRequest, servletResponse);
                        }
                        else{
                            LOGGER.log(Level.DEBUG,"Access to command "+command + "only for admin");
                            request.getRequestDispatcher(indexPath).forward(request, servletResponse);
                        }
                    }
                    else{
                        LOGGER.log(Level.DEBUG,"Access to command" + command + " denied for null users.");
                        request.getRequestDispatcher(indexPath).forward(request, servletResponse);
                    }
                }
                else{
                    LOGGER.log(Level.DEBUG, "Access denied to command " + command + " as session is null.");
                    request.getRequestDispatcher(indexPath).forward(request, servletResponse);
                }
            }
            else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
