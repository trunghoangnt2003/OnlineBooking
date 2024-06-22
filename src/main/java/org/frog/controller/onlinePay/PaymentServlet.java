package org.frog.controller.onlinePay;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


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
                System.out.println(money/100);
                System.out.println(request.getParameter("vnp_OrderInfo"));
                //PaymentDAO paymentDAO = new PaymentDAO();
                //int check = paymentDAO.insertPayment(id, user.getId(), money/100);
                 System.out.println("Da add giao dich vao data");
                resp.sendRedirect("Home");
            } else {
                resp.sendRedirect("Home");
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
