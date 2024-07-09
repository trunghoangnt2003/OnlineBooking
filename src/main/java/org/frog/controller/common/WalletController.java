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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

@WebServlet("/wallet/view")
public class WalletController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        WalletDAO walletDAO = new WalletDAO();
        Wallet wallet = walletDAO.getByAccountId(account.getId());
        ArrayList<Transaction> transactions = walletDAO.getAllTransactionByWalletId(account.getId());
        AccountDAO accountDAO = new AccountDAO();
        Account user = accountDAO.getAccountById(account.getId());
        int role = accountDAO.getRole(account.getId());

        ArrayList<Account> sender = new ArrayList<>();
        ArrayList<Account> receiver = new ArrayList<>();
        for(Transaction t : transactions){
            Account senderAccount = accountDAO.getAccountById(t.getWallet().getId());
            Account receiverAccount = accountDAO.getAccountById(t.getWalletOpposite().getId());
            t.setSender(senderAccount);
            t.setReceiver(receiverAccount);
        }

        Currency vnd = Currency.getInstance("VND");
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vndFormatter = NumberFormat.getCurrencyInstance(localeVN);

        vndFormatter.setCurrency(vnd);
        req.setAttribute("balance", vndFormatter.format(wallet.getBalance()));
        req.setAttribute("hold", vndFormatter.format(wallet.getHold()));

        req.setAttribute("role", role);
        req.setAttribute("transactions", transactions);
        req.setAttribute("wallet", wallet);
        req.setAttribute("account", user);
        req.setAttribute("sender", sender);
        req.setAttribute("receiver", receiver);
        req.getRequestDispatcher("../view/common/wallet.jsp").forward(req, resp);
    }
}
