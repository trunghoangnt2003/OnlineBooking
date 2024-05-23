package org.frog.controller.booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;

@WebServlet("/mentee/viewBooking")
public class BookingController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

            req.getRequestDispatcher("../view/booking/bookingRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
