package org.frog.fillter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.MentorDAO;
import org.frog.model.Account;

import java.io.IOException;
@WebFilter("/mentor/*")
public class MentorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String url =  httpRequest.getRequestURI();
        MentorDAO mentorDAO = new MentorDAO();
        HttpSession session = httpRequest.getSession();
        Account account = null;
        Object obj = session.getAttribute("account");
        if (obj != null) {
            account = (Account) obj;
        }
        if(account != null){
          if(url.contains("schedule")){
              if(mentorDAO.isCVexisted(account.getId()) ){
                  filterChain.doFilter(servletRequest, servletResponse);
              }
              else{
                  ((HttpServletResponse) servletResponse).sendRedirect("/Frog/mentor/create_profile");
              }
          }
          else if(!url.contains("schedule")){
              filterChain.doFilter(servletRequest, servletResponse);
          }else {
              ((HttpServletResponse) servletResponse).sendRedirect("Frog/error401");
          }
        }
        else{
            ((HttpServletResponse) servletResponse).sendRedirect("Frog/error401");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
