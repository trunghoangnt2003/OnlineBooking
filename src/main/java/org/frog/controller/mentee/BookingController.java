package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;

import java.io.IOException;
import java.util.List;

@WebServlet("/mentee/viewBooking")
public class BookingController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookingList = bookingDAO.getAllRequestProcessOfBooking(account.getId());
        List<Booking> bookingListC = bookingDAO.getAllRequestCancelOfBooking(account.getId());
        req.setAttribute("bookingListC", bookingListC);
        req.setAttribute("bookingList", bookingList);
        String bookingID = req.getParameter("idb");
        System.out.println("id b " + bookingID);
        if (bookingID != null) {
            bookingDAO.deleteBooking(bookingID);
            resp.sendRedirect("viewBooking");
            return;
        }
        req.getRequestDispatcher("../view/booking/bookingRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
