package org.frog.controller.common;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.utility.SHA1;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author trung
 */
@WebServlet("/change-pass")
public class ChangePassWordController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String passWord = req.getParameter("password1");
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.selectAll();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new Date());
        String account_email = "";
        boolean check = true;
        for(Account account:accounts){
            if(SHA1.toSHA1(account.getEmail()+date).equals(email)){
                account_email = account.getEmail();
                check=false;
                break;
            }
        }
        if(check) {
            resp.getWriter().println("Sorry, the link you provided has expired");
        }else{
            System.out.println("Change Pass : "+account_email);
            accountDAO.updatePassWord(account_email, SHA1.toSHA1(passWord));
            resp.sendRedirect("login");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/public/changePass.jsp").forward(req, resp);
    }

}
