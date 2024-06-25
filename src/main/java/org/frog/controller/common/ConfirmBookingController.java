package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.WalletDAO;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.PaymentEnum;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/confirmBooking")
public class ConfirmBookingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String bookingId = req.getParameter("id");
            Booking_ScheduleDAO bsDao = new Booking_ScheduleDAO();
            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = new Booking();
            WalletDAO walletDAO = new WalletDAO();
            Account mentor = new Account();
            Account mentee = new Account();
            int id = Integer.parseInt(bookingId);
            if (bsDao.getDetailBookingMentee(id).size() == bsDao.getNumberOfSlotConfirmed(id)) {
                bsDao.updateBooking(id, 13);
                booking = bookingDAO.getBookingById(id);
                mentor=walletDAO.getWalletAccountById(booking.getMentor().getAccount().getId());
                mentee=walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());

                float fee = (float) (booking.getAmount() * PaymentEnum.FEE);
                float menteePay = booking.getAmount() ;
                walletDAO.payment(mentee.getWallet().getBalance()-menteePay,mentee.getWallet().getId());
                walletDAO.updateAvailable(mentor.getWallet(),mentor.getWallet().getHold()-booking.getAmount());

                walletDAO.payment(mentor.getWallet().getBalance()+booking.getAmount()-fee,mentor.getWallet().getId());

                LocalDateTime now = LocalDateTime.now();
                walletDAO.createTransaction(Timestamp.valueOf(now),booking.getAmount(),mentee.getWallet().getId(),mentor.getWallet().getId(),fee);
                resp.getWriter().println("Thank you for confirming your booking");
                resp.getWriter().println("BOOKING");
                resp.getWriter().println("Mentee: "+booking.getMentee().getAccount().getName());
                resp.getWriter().println("Amount: "+booking.getAmount());
                resp.getWriter().println("Created date: "+booking.getDate());
                resp.getWriter().println("Skill: "+booking.getLevel_skills().getSkill().getName());
                resp.getWriter().println("Level: "+booking.getLevel_skills().getLevel().getName());
                resp.getWriter().println("Start Date: "+booking.getStartDate());
                resp.getWriter().println("End Date: "+booking.getEndDate());
                resp.getWriter().println("If you have any question please contact us at Frog Communication");
            } else {
                resp.getWriter().println("Sorry, your booking has not been confirmed yet");
                resp.getWriter().println("If you have any question please contact us at Frog Communication");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
