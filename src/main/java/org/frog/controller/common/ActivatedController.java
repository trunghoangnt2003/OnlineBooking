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
        Account accountCheck = null;
        boolean check = true;
        for(Account account:accounts){

            if(SHA1.toSHA1(account.getEmail()+account.getEmail()).equals(email)){

                if (account.getStatus().getId()==StatusEnum.INACTIVE){
                    accountCheck = account;
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
            accountDAO.updateStatusById(accountCheck.getId(), StatusEnum.ACTIVE);
            req.setAttribute("account", accountCheck);
            req.getRequestDispatcher("view/public/activated.jsp").forward(req, resp);
        }

    }
}
