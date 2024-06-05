package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/schedule/history")
public class HistoryScheduleController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Booking> bookingsHistory = new ArrayList<>();
        Booking_ScheduleDAO bs = new Booking_ScheduleDAO();
        bookingsHistory = bs.getBookingsHistory(account.getId());
        int numberOfInvited = bs.getNumberOfInvited(account.getId());
        int numberOfAccepted=bs.getNumberOfAccepted(account.getId());
        int numberOfRejected = bs.getNumberOfRejected(account.getId());
        int numberOfWaiting = bs.getNumberOfWaiting(account.getId());
        float rating = bs.getRating(account.getId());
        req.setAttribute("numberOfInvited",numberOfInvited);
        req.setAttribute("numberOfAccepted",numberOfAccepted);
        req.setAttribute("numberOfRejected",numberOfRejected);
        req.setAttribute("numberOfWaiting",numberOfWaiting);
        req.setAttribute("bookingsHistory",bookingsHistory);
        req.setAttribute("rating",rating);
        req.setAttribute("count", 1);
        req.getRequestDispatcher("/view/mentor/schedule/ViewHistoryBooking.jsp").forward(req, resp);
    }
}
