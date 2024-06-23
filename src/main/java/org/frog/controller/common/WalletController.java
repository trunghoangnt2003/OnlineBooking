package org.frog.controller.common;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.WalletDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Transaction;
import org.frog.model.Wallet;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/wallet/view")
public class WalletController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        WalletDAO walletDAO = new WalletDAO();
        Wallet wallet = walletDAO.getByAccountId(account.getId());
        ArrayList<Transaction> transactions = walletDAO.getAllTransactionByWalletId(wallet.getId());
        AccountDAO accountDAO = new AccountDAO();
        Account user = accountDAO.getAccountById(account.getId());
        int role = accountDAO.getRole(account.getId());

        req.setAttribute("role", role);
        req.setAttribute("transactions", transactions);
        req.setAttribute("wallet", wallet);
        req.setAttribute("account", user);
        req.getRequestDispatcher("../view/common/wallet.jsp").forward(req, resp);
    }
}
