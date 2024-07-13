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
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author trung
 */
@WebServlet("/reset-pass")
public class ResetPassWordController extends HttpServlet{
    private void sendEmail(String link,String email){
        String tile = "Reset your password";
        java.util.Date date = new Date();
        String content = "<i>Hello,"+email+" </i><br>"
                + "You have requested to reset your password.<br>"
                + "Click the link below to change your password:<br>"
                +  "<a href=\""+link+"\">Click me</a> <br>"
                + "<br>"
                + "Ignore this email if you do remember your password,"
                + "or you have not made the request<br>"
        + date;
        Email send = new Email();
        send.sendEmail(tile, content, email);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String userName = req.getParameter("username");
        String warningRP = "";
        String done;
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getLoginGoogle(email);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new Date());
        if(account!=null) {

            if(!account.getUserName().equals(userName)) {
                warningRP = "Username don't incorrect";
                req.setAttribute("warningRP", warningRP);
            }else {
                done = "Check your mail!";
                req.setAttribute("done", done);
                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                        + req.getContextPath() + "/change-pass?token=" + URLEncoder.encode(SHA1.toSHA1(account.getEmail() + date));
                sendEmail(url, email);
            }
        }else {
            warningRP = "Email don't incorrect";
            req.setAttribute("warningRP", warningRP);
        }

        req.getRequestDispatcher("view/public/resetPass.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/public/resetPass.jsp").forward(req, resp);
    }

}
