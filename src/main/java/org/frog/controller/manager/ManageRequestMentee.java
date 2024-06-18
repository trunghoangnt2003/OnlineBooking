package org.frog.controller.manager;

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

@WebServlet("/manager/request")
public class ManageRequestMentee extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        // Handle POST requests if needed
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String xpage = req.getParameter("page");
        String name = req.getParameter("name");
        String status = req.getParameter("status");
        String stDate = req.getParameter("stDate");
        String endDate = req.getParameter("endDate");
        String isOpen = req.getParameter("isOpenModal");
        String book_status = req.getParameter("book_status");
        int stId = 0;
        int bookStatus = 0;
        if(status != null) {
            if (status != "") {
                stId = Integer.parseInt(status);
            }
        }
        if (book_status != null) {
            bookStatus = Integer.parseInt(book_status);
        }
        int page = 1;
        if (xpage != null && !xpage.isEmpty()) {
            try {
                page = Integer.parseInt(xpage);
            } catch (NumberFormatException e) {
                // If parsing fails, default to page 1
                page = 1;
            }
        }

        int numberPage = 10;
        int offset = (page - 1) * numberPage;

        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> listBooking;
        int size;
            listBooking = bookingDAO.getBookingByName(stDate,endDate,stId,name, offset, numberPage);
            size = bookingDAO.getTotalBookingsCountByName(stDate,endDate,name, stId);
        int end_page = (size % numberPage == 0) ? (size / numberPage) : (size / numberPage) + 1;


        req.setAttribute("list", listBooking);
        req.setAttribute("size", size);
        req.setAttribute("page", page);
        req.setAttribute("end_page", end_page);
        req.setAttribute("status", status);
        req.setAttribute("name", name);
        req.setAttribute("stId",stId);
        req.setAttribute("stDate",stDate);
        req.setAttribute("endDate",endDate);
        req.setAttribute("isOpenModal",isOpen);
        req.getRequestDispatcher("../view/manager/viewRequestMentee.jsp").forward(req, resp);
    }
}



