package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/manageRequestMentee")
public class ManageRequestMentee extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        // Handle POST requests here if needed
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String xpage = req.getParameter("page");

        int page = 1;
        if (xpage != null) {
            page = Integer.parseInt(xpage);
        }
        int numberPage = 5;
        BookingDAO bookingDAO = new BookingDAO();

        List<Booking> allBookings = bookingDAO.getAllRequestOfMentees();
        int size = allBookings.size();
        int end_page = (size % numberPage == 0) ? (size / numberPage) : (size / numberPage) + 1;

        List<Booking> listBooking = bookingDAO.getAllRequestOfMentee(page, numberPage);
        req.setAttribute("list", listBooking);
        req.setAttribute("page", page);
        req.setAttribute("end_page", end_page);
        req.getRequestDispatcher("../view/admin/viewRequestMentee.jsp").forward(req, resp);
    }
}
