package org.frog.fillter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.FeatureDAO;
import org.frog.model.Account;

import java.io.IOException;


/**
 *
 * @author trung
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Account account = null;
        Object obj = session.getAttribute("account");
        if (obj != null) {
            account = (Account) obj;
        }
        FeatureDAO featureDAO = new FeatureDAO();
        // Kiểm tra đường dẫn yêu cầu
        String path = httpRequest.getRequestURI();

        String url = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort()
                + httpRequest.getContextPath();
        if (path.contains(".")&&!path.contains(".jsp")) {
            chain.doFilter(request, response);
            return;
        }
        if(featureDAO.checkPublic(path)){
            chain.doFilter(request, response);
            return;
        }
        if (account != null) {
            boolean result = featureDAO.checkRole(path,account.getRole().getId());
            if(result){
                chain.doFilter(request, response);
            }else{
                ((HttpServletResponse) response).sendRedirect(url+"/error401");
            }
        } else {

            ((HttpServletResponse) response).sendRedirect(url+"/login");

        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
