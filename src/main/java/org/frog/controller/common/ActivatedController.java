package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.utility.SHA1;
import org.frog.utility.StatusEnum;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/activate")
public class ActivatedController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("token");
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.selectAll();
        String account_email = "";
        boolean check = true;
        for(Account account:accounts){
            if(SHA1.toSHA1(account.getEmail()+account.getUserName()).equals(email)){
                if (account.getStatus().getId()==StatusEnum.INACTIVE){
                    account_email = account.getEmail();
                    check=false;
                    break;
                }else {
                    resp.getWriter().println("Your account has been activated");
                }
            }
        }
        if(check) {
            resp.getWriter().println("Sorry, the link you provided has expired");
        }else{
            resp.getWriter().println("account activated :" + account_email );
            accountDAO.updateStatus(account_email, StatusEnum.ACTIVE);
        }

    }
}
