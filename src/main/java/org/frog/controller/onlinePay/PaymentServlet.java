package org.frog.controller.onlinePay;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.TransactionDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;


/**
 *
 * @author trung
 */
@WebServlet("/checkPayment")
public class PaymentServlet extends AuthenticationServlet {

    private void payment(HttpServletRequest request, HttpServletResponse resp, Account user) throws ServletException, IOException {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                String id = request.getParameter("vnp_TxnRef");
                String moneyStr = request.getParameter("vnp_Amount");
                long money = Long.parseLong(moneyStr);
                TransactionDAO transactionDAO = new TransactionDAO();
                int check = transactionDAO.insertDeposit(user, (float) money /100);

                if(check>0) {
                    user.getWallet().setBalance((int) (user.getWallet().getBalance()+ (float) money /100));
                    HttpSession session = request.getSession();
                    session.setAttribute("account", user);

                    resp.sendRedirect("paymentSuccessful");
                }else {
                    request.setAttribute("paymentFail","failed");
                    request.getRequestDispatcher("Home").forward(request,resp);
                }
                } else {
                request.setAttribute("paymentFail","failed");
                request.getRequestDispatcher("Home").forward(request,resp);
            }

        } else {
            resp.getWriter().print("invalid signature");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        payment(req, resp, user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account user) throws ServletException, IOException {
        payment(req, resp, user);
    }

}
