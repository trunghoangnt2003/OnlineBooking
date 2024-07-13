package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.Mentor_ScheduleDAO;
import org.frog.DAO.WalletDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.model.BookingSchedule;
import org.frog.model.Mentor_Schedule;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/schedule/invite")
public class InviteRequestController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Booking> bookings = new ArrayList<>();
        Booking_ScheduleDAO bsDAO = new Booking_ScheduleDAO();
        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = new Booking();
        WalletDAO walletDAO = new WalletDAO();
        Account mentee = new Account();
        bookings = bsDAO.getBookingByMenteeBookMentor(account.getId());
        for(Booking b : bookings) {
           if(bookingDAO.isBookingExpired(b.getId())) {
               //money back
               booking = bookingDAO.getBookingById(b.getId());
               mentee=walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());
               walletDAO.moneyBack(mentee.getWallet().getHold()-booking.getAmount(),mentee.getWallet().getId());
               //save log
               ArrayList<BookingSchedule> bsSavelogs = new ArrayList<>();
               bsSavelogs=bsDAO.getBookingSchedulesById(b.getId());
               bsDAO.saveLog(bsSavelogs);
               //save schedule
               Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
               Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());
               ArrayList<BookingSchedule> bsSlots =bsDAO.checkSlotBookedByRejecSlot(b.getId());
               for(BookingSchedule bs : bsSlots){
                   int bsSlotsId = bsDAO.getIdSCheduleLogs(bs.getSchedule().getDate(),bs.getSchedule().getSlot().getId(),mentor_schedule.getId());
                   bsDAO.updateStatusScheduleLogs(bsSlotsId,11);
               }
               try {
                   // delete bs
                   boolean checkDBs = bsDAO.deleteScheduleBookings(b.getId());
                   // reject booking
                   if (checkDBs) {
                       bsDAO.rejectBooking(b.getId(), 2, "booking expired");
                   }
               }
               catch (Exception e) {
                   e.printStackTrace();
               }
               // remove booking
               bookings.remove(b);
           }
        }
        req.setAttribute("count", 1);
        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/view/mentor/schedule/InviteRequestBooking.jsp").forward(req, resp);
    }
}
