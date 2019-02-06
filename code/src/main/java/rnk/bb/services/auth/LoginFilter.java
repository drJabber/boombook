package rnk.bb.services.auth;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain next) throws IOException, ServletException {

        //Manually check that the current user can access pages
        //I did that by storing stuff in the session which you can access by
        //request.getSession().getAttribute(someKey);
        if(!isAuthorized(request)) {
            response.sendRedirect(request.getContextPath() + "/auth/login.xhtml");
            return;
        }

        next.doFilter(request, response);
    }

    private Boolean isAuthorized(HttpServletRequest request){
        if request.get
    }
}

