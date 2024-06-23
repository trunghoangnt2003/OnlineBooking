package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mentor/schedule")
public class ViewScheduleController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            String day = req.getParameter("today");
            ArrayList<BookingSchedule> schedules = new ArrayList<>();
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
//            String slotID = req.getParameter("slotID");
//            if (slotID != null) {
//                String[] infoSlotID = slotID.split("_");
//                SlotDAO slDAO = new SlotDAO();
//                Slot id = slDAO.getTimeSlot(Integer.parseInt(infoSlotID[1]));
//                if (DateTimeHelper.compareDayIDtoNow(infoSlotID[0], id.getStart_at(), id.getEnd_at())) {
//                    scheduleDAO.insertDayFreeByMentor(account.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));
//
//                } else {
//                    req.getSession().setAttribute("AddSlotError", "slot in passed");
//                }
//            }

            slots = scheduleDAO.getSlots();
            schedules = scheduleDAO.getSchedulesByIDnDay(account.getId(), DateTimeHelper.convertStringToDateByDay(weeks.get(0).getDayOfMonth()), DateTimeHelper.convertStringToDateByDay(weeks.get(6).getDayOfMonth()));
            bookings = bs.getBookingByMenteeBookMentor(account.getId());
            // kiem tra booking het han (sau 1 ngay)
//            for (Booking b : bookings) {
//                if (DateTimeHelper.checkExpiredBooking(b.getDate())) {
//                    bs.deleteScheduleBookings(b.getId());
//                    bs.updateBooking(b.getId(), 2);
//                    bookings.remove(b);
//
//                }
//            }
            BookingSlots = bs.getDetailBookings(account.getId());
            req.setAttribute("BookingSlots", BookingSlots);
            req.setAttribute("bookings", bookings);
            req.setAttribute("slots", slots);
            req.setAttribute("count", 1);
            req.setAttribute("mentorID", account.getId());
            req.setAttribute("schedules", schedules);
            req.getRequestDispatcher("../view/mentor/schedule/ViewMentorSchedule.jsp").forward(req, resp);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
