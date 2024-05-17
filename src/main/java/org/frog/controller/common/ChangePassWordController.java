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
import java.util.ArrayList;


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
        for(Account account:accounts){
            if(SHA1.toSHA1(account.getEmail()+account.getUserName()).equals(email)){
                System.out.println("Change Pass : "+account.getEmail());
                accountDAO.updatePassWord(account.getId(), SHA1.toSHA1(passWord));
                break;
            }
        }
        resp.sendRedirect("login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/public/changePass.jsp").forward(req, resp);
    }

}
