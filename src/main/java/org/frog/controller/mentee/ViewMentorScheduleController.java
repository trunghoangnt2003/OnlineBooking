package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.SlotDAO;
import org.frog.model.BookingSchedule;
import org.frog.model.Schedule;
import org.frog.model.Slot;
import org.frog.utility.DateTimeHelper;

import javax.mail.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mentee/schedule")
public class ViewMentorScheduleController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ymd_raw = request.getParameter("ymd");
        String mentor_id = "87928f9e-c513-4eea-9c29-0c403c57b537";
        String mentee_id = "e8fd47ed-dec2-49bf-829c-b182230ea49d";
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

        SlotDAO slotDAO = new SlotDAO();
        Booking_ScheduleDAO booking_scheduleDAO = new Booking_ScheduleDAO();

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

        ArrayList<BookingSchedule> bookingSchedules = booking_scheduleDAO.getBookingScheduleByMentor(mentor_id);


        for(BookingSchedule bs : bookingList) {
            System.out.println(bs.getSchedule().getId() + " " + bs.getSchedule().getSlot().getId() + " " + bs.getSchedule().getDate());
        }
        request.setAttribute("bookingList", bookingList);
        request.setAttribute("mentee_id", mentee_id);
        request.setAttribute("bookingSchedules", bookingSchedules);
        request.setAttribute("week", week);
        request.setAttribute("today", today);
        request.setAttribute("slots", slots);
        request.getRequestDispatcher("../view/mentee/schedule/view_mentor_schedule.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String[] bookings = request.getParameterValues("booking-schedule");

        if(bookings != null) {
            for (String booking : bookings) {
                System.out.println(booking);
                response.getWriter().println(booking);
            }
        }

    }
}