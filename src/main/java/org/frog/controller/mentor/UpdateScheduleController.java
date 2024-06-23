package org.frog.controller.mentor;

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

import java.io.IOException;

@WebServlet("/mentor/schedule/update")
public class UpdateScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

        try {
            String opt = req.getParameter("option");
            String bookingID = req.getParameter("bookingID");
            Booking_ScheduleDAO bsDAO = new Booking_ScheduleDAO();
            String today = req.getParameter("today");
            if(opt.equals("true")){

                 boolean checkDBs = bsDAO.updateScheduleBookings(Integer.parseInt(bookingID), 11);
                 if(checkDBs){
                     bsDAO.updateBooking(Integer.parseInt(bookingID), 11);
                     resp.sendRedirect("/Frog/mentor/schedule?today="+today);
                 }
            }else{
                String reason = req.getParameter("description");
                BookingDAO bookingDAO = new BookingDAO();
                Booking booking = new Booking();
                WalletDAO walletDAO = new WalletDAO();
                Account mentee = new Account();
                booking = bookingDAO.getBookingById(Integer.parseInt(bookingID));
                mentee=walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());
                walletDAO.moneyBack(mentee.getWallet().getAvailable()+booking.getAmount(),mentee.getWallet().getId());
                boolean checkDBs = bsDAO.deleteScheduleBookings(Integer.parseInt(bookingID));
                if(checkDBs){
                    bsDAO.rejectBooking(Integer.parseInt(bookingID), 2, reason);
                    resp.sendRedirect("/Frog/mentor/schedule?today="+today);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
