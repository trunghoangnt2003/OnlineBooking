package org.frog.controller.onlinePay;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@WebServlet("/paymentSuccessful")
public class paymentSuccessful extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        req.getRequestDispatcher("view/vnpay/paymentSuccessful.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        Currency usd = Currency.getInstance("USD");
        Locale loc = new Locale("hi", "IN");
        NumberFormat usdFormatter

                = NumberFormat.getCurrencyInstance(Locale.US);

        usdFormatter.setCurrency(usd);
        req.setAttribute("account",account);
        req.setAttribute("balance",usdFormatter.format(account.getWallet().getBalance()));
        req.getRequestDispatcher("view/vnpay/paymentSuccessful.jsp").forward(req, resp);
    }
}
