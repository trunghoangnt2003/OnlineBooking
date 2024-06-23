package org.frog.controller.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/manager/paymentBooking")
public class managePaymentBooking extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        int ITEMS_PER_PAGE = 20;
        try{
            ArrayList<Booking> bookingsHistory = new ArrayList<>();
            ArrayList<Booking> bookingsHistoryList = new ArrayList<>();
            Booking_ScheduleDAO bs = new Booking_ScheduleDAO();
            BookingDAO bDAO = new BookingDAO();
            bookingsHistoryList = bDAO.getAllBookingByMentee();
            // phan trang
            int currentPage = 1;
            String page = req.getParameter("page");
            if (page != null) {
                currentPage = Integer.parseInt(page);
            }

            int total = bookingsHistoryList.size();
            int totalPages = (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
            int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, total);
            for(int i = startIndex; i < endIndex ; i++) {
                bookingsHistory.add(bookingsHistoryList.get(i));
            }
            req.setAttribute("bookingsHistory",bookingsHistory);
            req.setAttribute("count", 1);
            req.getRequestDispatcher("/view/manager/managePaymentBooking.jsp").forward(req, resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
