package org.frog.controller.common;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.WallletDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Transaction;
import org.frog.model.Wallet;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet("/wallet/view")
public class WalletController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String id = req.getParameter("id");
        WallletDAO wallletDAO = new WallletDAO();
        Wallet wallet = wallletDAO.getInfoWalletById(id);
        ArrayList<Transaction> transactions = wallletDAO.getAllTransactionByWalletId(wallet.getId());

        req.setAttribute("transactions", transactions);
        req.setAttribute("wallet", wallet);
        req.setAttribute("name", account.getName());
        req.getRequestDispatcher("../view/common/wallet.jsp").forward(req, resp);
    }
}
