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
                // accept booking
                 boolean checkDBs = bsDAO.updateScheduleBookings(Integer.parseInt(bookingID), 11);
                if(checkDBs){
                    bsDAO.updateBooking(Integer.parseInt(bookingID), 11);

                }
                 // check another booking has duplicate slot
                 ArrayList<Booking> bookingIdSaveLogs = new ArrayList<>();
                 bookingIdSaveLogs = bsDAO.getBookingIdDuplicateSlotByBookingID(Integer.parseInt(bookingID));
                ArrayList<BookingSchedule> bsSavelogs = new ArrayList<>();
                Booking booking = new Booking();
                BookingDAO bookingDAO = new BookingDAO();
                WalletDAO walletDAO = new WalletDAO();
                Account mentee = new Account();
                // run if found
                 for(Booking b : bookingIdSaveLogs){
                     //save log
                     bsSavelogs = bsDAO.getBookingSchedulesById(b.getId());
                     bsDAO.saveLog(bsSavelogs);
                     // money back
                     booking = bookingDAO.getBookingById(b.getId());
                     mentee = walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());
                     walletDAO.moneyBack(mentee.getWallet().getHold()-booking.getAmount(),mentee.getWallet().getId());
                     //delete bs
                     bsDAO.deleteScheduleBookings(b.getId());
                     // reject booking
                     bsDAO.rejectBooking(b.getId(), 2, "booking has book by another mentee");
                 }
                 // update status booking to accept
                resp.sendRedirect("/Frog/mentor/schedule?today="+today);
            }else{
                String reason = req.getParameter("description");
                BookingDAO bookingDAO = new BookingDAO();
                Booking booking = new Booking();
                WalletDAO walletDAO = new WalletDAO();
                Account mentee = new Account();
                //money back
                booking = bookingDAO.getBookingById(Integer.parseInt(bookingID));
                mentee=walletDAO.getWalletAccountById(booking.getMentee().getAccount().getId());
                walletDAO.moneyBack(mentee.getWallet().getHold()-booking.getAmount(),mentee.getWallet().getId());
                //save log
                ArrayList<BookingSchedule> bsSavelogs = new ArrayList<>();
                bsSavelogs=bsDAO.getBookingSchedulesById(Integer.parseInt(bookingID));
                bsDAO.saveLog(bsSavelogs);
                //save schedule
                Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
                Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());
                ArrayList<BookingSchedule> bsSlots =bsDAO.checkSlotBookedByRejecSlot(Integer.parseInt(bookingID));
                for(BookingSchedule bs : bsSlots){
                    int bsSlotsId = bsDAO.getIdSCheduleLogs(bs.getSchedule().getDate(),bs.getSchedule().getSlot().getId(),mentor_schedule.getId());
                    bsDAO.updateStatusScheduleLogs(bsSlotsId,11);
                }
                // delete bs
                boolean checkDBs = bsDAO.deleteScheduleBookings(Integer.parseInt(bookingID));
                // reject booking
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
