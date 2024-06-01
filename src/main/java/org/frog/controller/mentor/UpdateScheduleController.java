package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.BookingSchedule;
import org.frog.model.Schedule;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

@WebServlet("/mentor/schedule/update")
public class UpdateScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            String opt = req.getParameter("option");
            String bookingID = req.getParameter("bookingID");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
