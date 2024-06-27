package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.DAO.WalletDAO;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.PaymentEnum;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet("/confirmBooking")
public class ConfirmBookingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String bookingId = req.getParameter("id");
            Booking_ScheduleDAO bsDao = new Booking_ScheduleDAO();
            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = new Booking();
//            WalletDAO walletDAO = new WalletDAO();
//            Account mentor = new Account();
//            Account mentee = new Account();
            int id = Integer.parseInt(bookingId);
            booking = bookingDAO.getBookingById(id);

            if (bsDao.getDetailBookingMentee(id).size() == bsDao.getNumberOfSlotConfirmed(id)) {
//                if(booking.getStatus().getId() != 13){
//                    bsDao.updateBooking(id, 13);
//                    mentor=walletDAO.getWalletAccountById(booking.getMentor().getAccount().getId());
//                    mentee=walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());
//
//                    float fee = (float) (booking.getAmount() * PaymentEnum.FEE);
//                    float menteePay = booking.getAmount() ;
//                    walletDAO.payment(mentee.getWallet().getBalance()-menteePay,mentee.getWallet().getId());
//                    walletDAO.updateAvailable(mentor.getWallet(),mentor.getWallet().getHold()-booking.getAmount());
//
//                    walletDAO.payment(mentor.getWallet().getBalance()+booking.getAmount()-fee,mentor.getWallet().getId());
//
//                    LocalDateTime now = LocalDateTime.now();
//                    walletDAO.createTransaction(Timestamp.valueOf(now),booking.getAmount(),mentee.getWallet().getId(),mentor.getWallet().getId(),fee);
//                }
                Booking_ScheduleDAO bDAO = new Booking_ScheduleDAO();
                               if(booking.getStatus().getId() !=13 && booking.getStatus().getId() !=3  ){
                       bDAO.updateBooking(id,3);
                   }


                req.setAttribute("book", booking);
//
                req.getRequestDispatcher("/view/common/ConfirmBooking.jsp").forward(req, resp);
            } else {
                resp.getWriter().println("Sorry, your booking has not been confirmed yet");
                resp.getWriter().println("If you have any question please contact us at Frog Communication");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
