package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.model.Status;
import org.frog.utility.Email;
import org.frog.utility.SHA1;
import org.frog.utility.StatusEnum;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private void sendEmail(String link,String email){
        String tile = "Activate Your Account Now!";
        String content = "<h3>Dear "+email+"</h3><br>" +
                "<br>" +
                "Congratulations on creating your account with us! To activate your account and get started right away, simply click on the following link: " +
                "<a href = \""+link+"\" >[Activation]</a><br>" +
                "<br>" +
                "Once you click on the link, you will be directed to the activation page where you can provide any necessary information and complete the activation process.<br>" +
                "<br>" +
                "If you have any questions or encounter any difficulties, please feel free to contact our support team at [<i>0942532003</i>]. We're here to assist you.<br>" +
                "<br>" +
                "Thank you for choosing our platform. We look forward to serving you!<br>" +
                "<br>" +
                "Best regards";
        Email send = new Email();
        send.sendEmail(tile, content, email);

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("view/public/login.jsp").forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String passWord = req.getParameter("passWord");
        passWord = SHA1.toSHA1(passWord);
        String email = req.getParameter("email");
        String warningRegister;
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getLoginGoogle(email);
        if(account!=null){
            warningRegister = "Account already exists";
            req.setAttribute("warningRegister", warningRegister);
            req.getRequestDispatcher("view/public/login.jsp").forward(req,res);
        }
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        accountDAO.register(id,email,passWord,1);
        warningRegister = "Please check your mail to activate your account.";
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/activate?token=" + SHA1.toSHA1(email+email);
        sendEmail(url,email);
        req.setAttribute("warningRegister", warningRegister);
        req.getRequestDispatcher("view/public/login.jsp").forward(req,res);

    }

}
