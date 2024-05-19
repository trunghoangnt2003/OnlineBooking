//package org.frog.controller.auth;/*

//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.frog.model.Account;
//
//import java.io.IOException;
//
//
///**
// *
// * @author trung
// */
//@WebFilter("/*")
//public class AuthFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpSession session = httpRequest.getSession();
//        Account account = null;
//        Object obj = session.getAttribute("user");
//        if (obj != null) {
//            account = (Account) obj;
//        }
//        // Kiểm tra đường dẫn yêu cầu
//        String path = httpRequest.getRequestURI();
//        System.out.println(path);
//        if (path.equals("/frog/reset-pass")||path.equals("/frog/change-pass")||path.contains("/fap/img")||path.equals("/fap/login")||path.equals("/fap/google") || path.contains("/fap/css") || path.contains("/fap/js")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        String url = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort()
//                + httpRequest.getContextPath();
//        if (user != null) {
//            FluterDAO fluterDAO = new FluterDAO();
//            int result = fluterDAO.fluterRole(user.getId(), path);
//            if(result!=0){
//                chain.doFilter(request, response);
//                return;
//            }else{
//                ((HttpServletResponse) response).sendRedirect(url+"/logout");
//            }
//        } else {
//
//            ((HttpServletResponse) response).sendRedirect(url+"/login");
//
//        }
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//    }
//
//}
