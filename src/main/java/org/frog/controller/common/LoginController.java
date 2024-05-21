package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.utility.SHA1;
import org.frog.utility.StatusEnum;

import java.io.IOException;
@WebServlet("/login")
public class LoginController extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("view/public/signin.jsp").forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String userName = req.getParameter("username");
        String passWord = req.getParameter("password");
        passWord = SHA1.toSHA1(passWord);
        String warningLogin;
        String url;
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getLogin(userName, passWord);

        if(account == null){
            url = "view/public/login.jsp";
            warningLogin = "email or password incorrect";
            req.setAttribute("warningLogin", warningLogin);
            req.getRequestDispatcher(url).forward(req, res);
        }else {
            if(account.getStatus().getId()== StatusEnum.INACTIVE){
                url = "view/public/login.jsp";
                warningLogin = "Account need to be activated";
                req.setAttribute("warningLogin", warningLogin);
                req.getRequestDispatcher(url).forward(req, res);
            }
            HttpSession session = req.getSession();
            session.setAttribute("account", account);

            Cookie c_username = new Cookie("userName", userName);
            c_username.setMaxAge(3600 * 24 * 7);

            Cookie c_password = new Cookie("passWord", passWord);
            c_password.setMaxAge(3600 * 24 * 7);

            res.addCookie(c_username);
            res.addCookie(c_password);
            res.sendRedirect("home");
        }
    }

}
