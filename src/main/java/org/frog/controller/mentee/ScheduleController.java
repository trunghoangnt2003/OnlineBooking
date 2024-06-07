package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.DAO.SlotDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/mentee/schedule")
public class ScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String ymd_raw = request.getParameter("ymd");

        String mentee_id = account.getId();
        String[] bookings = request.getParameterValues("booking-schedule");

        ArrayList<BookingSchedule> bookingList = new ArrayList<>();
        if(bookings != null) {
            for (String booking : bookings) {
                String[] array_bookings = booking.split("_");
                int scheduledId = Integer.parseInt(array_bookings[0]);
                int slotId = Integer.parseInt(array_bookings[1]);
                String date = array_bookings[2];

                Schedule s = new Schedule();
                s.setId(scheduledId);
                Slot slot = new Slot();
                slot.setId(slotId);
                s.setSlot(slot);
                s.setDate(java.sql.Date.valueOf(date));

                BookingSchedule bs = new BookingSchedule();
                bs.setSchedule(s);

                bookingList.add(bs);
            }

        }


        Level_SkillDAO level_skillDAO = new Level_SkillDAO();
        Booking_ScheduleDAO booking_scheduleDAO = new Booking_ScheduleDAO();
        SlotDAO slotDAO = new SlotDAO();
        ArrayList<Slot> slots = slotDAO.selectAll();
        Date toDay = new Date();
        java.sql.Date from = null;
        java.sql.Date to = null;
        java.sql.Date today = null;
        if (ymd_raw == null) {
            from = DateTimeHelper.convertUtilDateToSqlDate(DateTimeHelper.getWeekStart(toDay));
            to = DateTimeHelper.convertUtilDateToSqlDate(DateTimeHelper.
                    addDaysToDate(DateTimeHelper.getWeekStart(toDay), 6));
            today = DateTimeHelper.convertUtilDateToSqlDate(toDay);
        } else {
            Date ymd = java.sql.Date.valueOf(ymd_raw);
            from = DateTimeHelper.convertUtilDateToSqlDate(DateTimeHelper.getWeekStart(ymd));
            to = DateTimeHelper.convertUtilDateToSqlDate(DateTimeHelper.
                    addDaysToDate(from, 6));
            today = DateTimeHelper.convertUtilDateToSqlDate(ymd);
        }
        ArrayList<java.sql.Date> week = DateTimeHelper.getDatesBetween(from, to);
        ScheduleDAO booking_scheduleDAO1 = new ScheduleDAO();
        ArrayList<BookingSchedule> bookingSchedules = booking_scheduleDAO1.getAllBookSchedule(mentee_id);


        request.setAttribute("bookingList", bookingList);
        request.setAttribute("mentee_id", mentee_id);
        request.setAttribute("bookingSchedules", bookingSchedules);
        request.setAttribute("week", week);
        request.setAttribute("today", today);
        request.setAttribute("slots", slots);
        request.getRequestDispatcher("../view/mentee/schedule.jsp").forward(request, resp);
    }

}
