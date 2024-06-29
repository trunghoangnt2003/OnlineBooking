package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
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
public class ScheduleDashboardController extends AuthenticationServlet {

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
            BookingDAO bookingDAO = new BookingDAO();
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
            if(bookingDAO.isNewBooking(account.getId())) {
                req.setAttribute("newBooking", "yes");
            }

            slots = scheduleDAO.getSlots();
            schedules = scheduleDAO.getSchedulesByIDnDay(account.getId(), DateTimeHelper.convertStringToDateByDay(weeks.get(0).getDayOfMonth()), DateTimeHelper.convertStringToDateByDay(weeks.get(6).getDayOfMonth()));
            bookings = bs.getBookingByMenteeBookMentor(account.getId());

            BookingSlots = bs.getDetailBookings(account.getId());
            // kiem tra tuan sau co trong slot khong
            List<Week> weeksCheck = new ArrayList<>();
            weeksCheck = DateTimeHelper.getWeekDates(DateTimeHelper.getFutureDate(1));
            ArrayList<BookingSchedule> isNextWeekFree = new ArrayList<>();
            isNextWeekFree =  scheduleDAO.getSchedulesByIDnDay(account.getId(), DateTimeHelper.convertStringToDateByDay(weeksCheck.get(0).getDayOfMonth()), DateTimeHelper.convertStringToDateByDay(weeksCheck.get(6).getDayOfMonth()));
            if(schedules.size()==0){
                req.setAttribute("isStart", "yes");
            }else {
                if(isNextWeekFree.size() == 0){
                    req.setAttribute("isFree", "yes");
                }
            }

            //--------------------------------------
            req.setAttribute("today", day);
            req.setAttribute("BookingSlots", BookingSlots);
            req.setAttribute("bookings", bookings);
            req.setAttribute("slots", slots);
            req.setAttribute("count", 1);
            req.setAttribute("mentorID", account.getId());
            req.setAttribute("schedules", schedules);
            req.getRequestDispatcher("/view/mentor/schedule/ScheduleDashboard.jsp").forward(req, resp);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
