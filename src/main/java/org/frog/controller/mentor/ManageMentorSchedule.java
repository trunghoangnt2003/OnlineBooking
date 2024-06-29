package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mentor/schedule/manage")
public class ManageMentorSchedule extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
       try{
           BookingDAO bsDao = new BookingDAO();
           String day = req.getParameter("today");
           String id = req.getParameter("id");
           List<Week> weeks = new ArrayList<>();
           ArrayList<Booking> menteeBooking = new ArrayList<>();
           ArrayList<Slot> slots = new ArrayList<>();
           ArrayList<BookingSchedule> schedules = new ArrayList<>();
           ArrayList<BookingSchedule> bookingSlots = new ArrayList<>();
           Booking_ScheduleDAO bDAO = new Booking_ScheduleDAO();
           if (day == null) {
               LocalDate date = LocalDate.now();
               day = DateTimeHelper.convertDateToString(date);
               weeks = DateTimeHelper.getWeekDates(day);
               req.setAttribute("weeks", weeks);
           } else {
               weeks = DateTimeHelper.getWeekDates(day);
               req.setAttribute("weeks", weeks);
           }
           ScheduleDAO scheduleDAO = new ScheduleDAO();

           slots = scheduleDAO.getSlots();
           schedules = scheduleDAO.getSchedulesByIDnDay(account.getId(), DateTimeHelper.convertStringToDateByDay(weeks.get(0).getDayOfMonth()), DateTimeHelper.convertStringToDateByDay(weeks.get(6).getDayOfMonth()));
           menteeBooking = bsDao.getMyBookingByMentee(account.getId());
           if(id != null){
               bookingSlots = bDAO.getDetailBookingMentee(Integer.parseInt(id));
               Booking b = bsDao.getBookingById(Integer.parseInt(id));
               // auto set done booking
//               if(b.getStatus().getId() !=13){
//                   if(bookingSlots.size() == bDAO.getNumberOfSlotConfirmed(Integer.parseInt(id))){
//                       bDAO.updateBooking(Integer.parseInt(id),3);
//                   }
//               }
               req.setAttribute("bookingSlots", bookingSlots);
               req.setAttribute("bookingSlotsNumber",bookingSlots.size());
               req.setAttribute("slotConfirmedNumber",bDAO.getNumberOfSlotConfirmed(Integer.parseInt(id)));
           }
           req.setAttribute("count", 1);
           req.setAttribute("menteeBooking", menteeBooking);
           req.setAttribute("slots", slots);
           req.setAttribute("schedules", schedules);
           req.setAttribute("today",day);
       }catch (Exception e) {
           e.printStackTrace();
       }
        req.getRequestDispatcher("/view/mentor/schedule/ManageMentorSchedule.jsp").forward(req, resp);

    }
}
