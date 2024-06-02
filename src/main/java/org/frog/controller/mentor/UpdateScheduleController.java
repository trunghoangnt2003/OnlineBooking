package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.JDBC;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.BookingSchedule;
import org.frog.model.Schedule;
import org.frog.utility.DateTimeHelper;
import org.hibernate.Session;

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
            Booking_ScheduleDAO bsDAO = new Booking_ScheduleDAO();
            if(opt.equals("true")){
                 boolean checkDBs = bsDAO.updateScheduleBookings(Integer.parseInt(bookingID), 11);
                 if(checkDBs){
                     bsDAO.updateBooking(Integer.parseInt(bookingID), 11);
                     req.getSession().setAttribute("updateStatus", "schedule confirmed");
                     resp.sendRedirect("/Frog/mentor/schedule");
                 }
                 else{
                     req.getSession().setAttribute("updateStatus", "schedule not confirmed");
                     resp.sendRedirect("/Frog/mentor/schedule");
                 }
            }else{
                boolean checkDBs = bsDAO.deleteScheduleBookings(Integer.parseInt(bookingID));
                if(checkDBs){
                    bsDAO.updateBooking(Integer.parseInt(bookingID), 2);
                    req.getSession().setAttribute("rejectStatus", "schedule rejected");
                    resp.sendRedirect("/Frog/mentor/schedule");
                }
                else{
                    req.getSession().setAttribute("rejectStatus", "schedule not rejected");
                    resp.sendRedirect("/Frog/mentor/schedule");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
