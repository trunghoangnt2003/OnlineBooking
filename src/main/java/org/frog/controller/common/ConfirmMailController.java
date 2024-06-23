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
import java.util.Date;

@WebServlet("/confirmMail")
public class ConfirmMailController extends HttpServlet {
    private void sendEmail(String link, Account account){
        java.util.Date date = new Date();
        String tile = "All lessons was successfully completed!";
        String content = "<h4>Dear "+account.getName()+"</h4><br>" +
                "<br>" +
                "Congratulations! All lessons were successfully completed. Thank you for your efforts!" +
                "<br>"+
                "If you have any questions or encounter any difficulties, please feel free to contact our support team at Frog Community. We're here to assist you.<br>" +
                "<br>" +
                "Please click on the link below to confirm our lessons:<br>" +
                "<br>" +
                "<a href = \""+link+"\" >[confirm]</a><br>" +
                "<br>" +
                "I will hope see you in another class!<br>" +
                "<br>" +
                "Thank you for choosing our platform. We look forward to serving you!<br>" +
                "<br>" +
                "Best regards<br>"
                +date;
        Email send = new Email();
        send.sendEmail(tile, content, account.getEmail());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String menteeId=req.getParameter("menteeId");
        String bookingId=req.getParameter("bookingId");
        AccountDAO accDao = new AccountDAO();
        Account mentee = accDao.getAccountById(menteeId);
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/confirmBooking?id=" +bookingId;
        sendEmail(url, mentee);
        resp.sendRedirect("/Frog/mentor/schedule/manage?id="+bookingId+"&action=3");
    }
}
