package org.frog.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.utility.SHA1;

import java.io.IOException;
@WebServlet("/changePass")
public class ChangePass extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String oldPass = req.getParameter("password");
        String newPass = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        String sha1PassWord = SHA1.toSHA1(oldPass);
        if (account.getPassword().equals(sha1PassWord)) {
            if(newPass.equals(oldPass)) {
                req.setAttribute("error", "New password is the same as the old password");
                req.getRequestDispatcher("view/common/changePass.jsp").forward(req, resp);
            } else if(newPass.equals(confirmPassword)) {
                account.setPassword(SHA1.toSHA1(newPass));
                AccountDAO accountDAO = new AccountDAO();
                accountDAO.ChangePassword(account);
                SHA1.toSHA1(newPass);
                req.setAttribute("success", "Password changed successfully");
                req.getRequestDispatcher("view/common/changePass.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Confirm password is incorrect");
                req.getRequestDispatcher("view/common/changePass.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Wrong password, please try again");
            req.getRequestDispatcher("view/common/changePass.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        req.getRequestDispatcher("view/common/changePass.jsp").forward(req, resp);
    }
}
