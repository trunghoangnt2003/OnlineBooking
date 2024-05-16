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
import java.util.ArrayList;

@WebServlet("/activate")
public class ActivatedController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("token");
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.selectAll();
        for(Account account:accounts){

            if(SHA1.toSHA1(account.getEmail()+account.getUserName()).equals(email)){
                if (account.getStatus().getId()==StatusEnum.INACTIVE){
                    resp.getWriter().println("<h3>account activated :"+account.getEmail() + " "+ StatusEnum.ACTIVE+"</h3>");
                    accountDAO.updateStatus(account.getEmail(), StatusEnum.ACTIVE);
                }else {
                    resp.getWriter().println("<h2>Your account has been activated</2>");
                }
            }
        }

    }
}
