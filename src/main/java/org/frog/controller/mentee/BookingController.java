package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.model.BookingSchedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mentee/viewBooking")
public class BookingController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String bookingID = req.getParameter("idb");

        BookingDAO bookingDAO = new BookingDAO();
        Booking_ScheduleDAO bsDAO = new Booking_ScheduleDAO();
        List<Booking> bookingList = bookingDAO.getAllRequestProcessOfBooking(account.getId());
        List<Booking> bookingListC = bookingDAO.getAllRequestCancelOfBooking(account.getId());
        req.setAttribute("bookingListC", bookingListC);
        req.setAttribute("bookingList", bookingList);
        if (bookingID != null) {
            try {
                int id = Integer.parseInt(bookingID);
                ArrayList<BookingSchedule> bookingSchedules = bsDAO.getAllByBookingId(id);
                bsDAO.saveLog(bookingSchedules);
                bookingDAO.cancelBooking(bookingID);
                bsDAO.deleteScheduleBookings(Integer.parseInt(bookingID));

                resp.sendRedirect("viewBooking");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        req.getRequestDispatcher("../view/mentee/bookingManage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
