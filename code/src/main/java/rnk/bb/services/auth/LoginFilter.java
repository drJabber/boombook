package rnk.bb.services.auth;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginFilter extends HttpFilter {
    private Map<String, List<String>> restrictions;

    private FilterConfig config;

    @Override
    public void init (FilterConfig config) throws ServletException
    { 
        this.config = config;
        restrictions=new HashMap<>();
        List<String> alllist=new ArrayList<>();
        restrictions.put("all",alllist);
        
        String restrictions_string = config.getInitParameter("restrictions"); 
        if (restrictions_string != null) {
            String[] parts=restrictions_string.split(";");
            for (String part:parts){
                String[] r=part.split(":");
                String[] patterns=r[1].split(",");
                
                List<String> list=new ArrayList<String>();
                restrictions.put(r[0],list);
                for(String pattern:patterns){
                    list.add(pattern);
                    if (alllist.indexOf(pattern)<0) {
                        alllist.add(pattern);
                    }
                }
            }
        }
    } 

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain next) throws IOException, ServletException {
        if(!isAuthorized(request)) {
            String requestURI  = request.getRequestURI();
            String queryString = request.getQueryString();
            String encodedURL  = URLEncoder.encode(requestURI + "?" + queryString, "UTF-8");
            response.sendRedirect(request.getContextPath() + "/auth/login.xhtml?originalURL=" + encodedURL);            
            return;
        }
        next.doFilter(request, response);
    }

    private Boolean isAuthorized(HttpServletRequest request){
        String uri=request.getRequestURI().trim();
        String contextPath=request.getContextPath();
        List<String> alllist=restrictions.get("all");
        HttpSession session = request.getSession(false);

        Principal principal=request.getUserPrincipal();
        Boolean isAuthorized=(principal!=null)&&(principal.getName()!=null);

        for (String restriction:alllist){
            if (uri.startsWith(contextPath+"/"+restriction+"/")){
                return isAuthorized && isAllowedForCurrentUser(restriction, request);
            }
        }

        return true;
    }

    private Boolean isAllowedForCurrentUser(String pattern, HttpServletRequest request){
        for (Map.Entry<String, List<String>> entry:restrictions.entrySet()){
            if (request.isUserInRole(entry.getKey())){
                if (entry.getValue().indexOf(pattern)>=0){
                    return true;
                }
            }
        }
        return false;
    }
}

