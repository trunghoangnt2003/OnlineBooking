package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.utility.Email;
import org.frog.utility.SHA1;

import java.io.IOException;



/**
 *
 * @author trung
 */
@WebServlet("/reset-pass")
public class ResetPassWordController extends HttpServlet{
    private void sendEmail(String link,String email){
        String tile = "Reset your password";
        String content = "Hello,"+email+" \n"
                + "You have requested to reset your password.\n"
                + "Click the link below to change your password:"
                +  "<a href=\""+link+"\">Click me</a> \n"
                + "\n"
                + "Ignore this email if you do remember your password, "
                + "or you have not made the request";
        Email send = new Email();
        send.sendEmail(tile, content, email);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String warningRP = "";

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getLoginGoogle(email);
        if(account!=null) {
            warningRP = "Check your mail!";
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath() + "/change-pass?token=" + SHA1.toSHA1(account.getEmail()+account.getUserName());
            sendEmail(url, email);
        }else {
            warningRP = "Email don't incorrect";
        }
        req.setAttribute("warningRP", warningRP);
        req.getRequestDispatcher("view/public/login.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/public/login.jsp").forward(req, resp);
    }

}
