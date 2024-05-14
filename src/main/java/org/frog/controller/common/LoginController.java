package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;

import java.io.IOException;
@WebServlet("/login")
public class LoginController extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("view/public/login.jsp").forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String username = req.getParameter("userName");
        String password = req.getParameter("passWord");

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getLogin(username, password);

        res.getWriter().println(account.getUserName());
    }

}
