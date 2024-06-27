package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.Mentor_ScheduleDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mentor/schedule/edit")
public class ViewScheduleController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            String day = req.getParameter("today");
            ArrayList<Schedule> schedules = new ArrayList<>();
            ArrayList<BookingSchedule> BookingSlots = new ArrayList<>();
            ArrayList<Slot> slots = new ArrayList<>();
            List<Week> weeks = new ArrayList<>();
            MentorDAO mentorDAO = new MentorDAO();
            ScheduleDAO scheduleDAO = new ScheduleDAO();
            Booking_ScheduleDAO bs = new Booking_ScheduleDAO();
            ArrayList<Booking> bookings = new ArrayList<>();

            if (day == null) {
                LocalDate date = LocalDate.now();
                day = DateTimeHelper.convertDateToString(date);
                weeks = DateTimeHelper.getWeekDates(day);
                req.setAttribute("weeks", weeks);
            } else {
                weeks = DateTimeHelper.getWeekDates(day);
                req.setAttribute("weeks", weeks);
            }

            String dayID = req.getParameter("dayID");
            if (dayID != null) {

                String[] infoDayID = dayID.split("_");
                ArrayList<BookingSchedule> bookInfo = bs.getDetailBooking(account.getId(), DateTimeHelper.convertStringToDateByDay(infoDayID[0]), Integer.parseInt(infoDayID[1]));
                req.setAttribute("bookInfo", bookInfo);

            }
            slots = scheduleDAO.getSlots();
            schedules = scheduleDAO.getScheduleLogsByMentorSet(account.getId());
            bookings = bs.getBookingByMenteeBookMentor(account.getId());
            BookingSlots = bs.getDetailBookings(account.getId());
            // check slot booked or not
            // slot always exist in schedule logs
            ArrayList<BookingSchedule> bsCheckSlotBooked = bs.checkSlotBooked(account.getId());
            int idCheckSlotBooked = 0;
            Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
            Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());
            for( BookingSchedule b : bsCheckSlotBooked){
                idCheckSlotBooked = bs.getIdSCheduleLogs(b.getSchedule().getDate(),b.getSchedule().getSlot().getId(),mentor_schedule.getId());
               if(idCheckSlotBooked != 0){
                   bs.updateStatusScheduleLogs(idCheckSlotBooked,9);
               }
            }

            req.setAttribute("today", day);
            req.setAttribute("BookingSlots", BookingSlots);
            req.setAttribute("bookings", bookings);
            req.setAttribute("slots", slots);
            req.setAttribute("count", 1);
            req.setAttribute("mentorID", account.getId());
            req.setAttribute("schedules", schedules);
            req.getRequestDispatcher("/view/mentor/schedule/ViewMentorSchedule.jsp").forward(req, resp);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
