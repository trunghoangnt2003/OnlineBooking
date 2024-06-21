//package org.frog.controller.onlinePay;
//
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.frog.controller.auth.AuthenticationServlet;
//import org.frog.model.Account;
//
//import java.io.IOException;
//
//@WebServlet("Payment")
//public class PaymentController extends AuthenticationServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
//        req.getRequestDispatcher("view/onlinePay/payment.jsp").forward(req,resp);
//    }
//}
