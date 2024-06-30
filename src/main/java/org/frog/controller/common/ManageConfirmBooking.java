package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.WalletDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.utility.PaymentEnum;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/manageConfirmBooking")
public class ManageConfirmBooking extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String bookingId = req.getParameter("id");
        Booking_ScheduleDAO bsDao = new Booking_ScheduleDAO();
        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = new Booking();
            WalletDAO walletDAO = new WalletDAO();
            Account mentor = new Account();
            Account mentee = new Account();
        int id = Integer.parseInt(bookingId);
        booking = bookingDAO.getBookingById(id);
                        if(booking.getStatus().getId() == 3){
                            try {
                                bsDao.updateBooking(id, 13);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            mentor=walletDAO.getWalletAccountById(booking.getMentor().getAccount().getId());
                    mentee=walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());

                    float fee = (float) (booking.getAmount() * PaymentEnum.FEE);
                    float menteePay = booking.getAmount() ;
                    walletDAO.payment(mentee.getWallet().getBalance()-menteePay,mentee.getWallet().getId());
                    walletDAO.updateAvailable(mentee.getWallet(),mentee.getWallet().getHold()-booking.getAmount());

                    walletDAO.payment(mentor.getWallet().getBalance()+booking.getAmount()-fee,mentor.getWallet().getId());

                    LocalDateTime now = LocalDateTime.now();
                    walletDAO.createTransaction(Timestamp.valueOf(now),booking.getAmount(),mentee.getWallet().getId(),mentor.getWallet().getId(),fee);
                }
        resp.sendRedirect("/Frog/manager/paymentBooking");

    }
}
