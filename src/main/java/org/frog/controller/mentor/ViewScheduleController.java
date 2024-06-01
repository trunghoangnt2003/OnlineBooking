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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mentor/schedule")
public class ViewScheduleController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try{
            String setDate= req.getParameter("setDate");
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            MentorDAO mentorDAO = new MentorDAO();
            //mentorDAO.updateFreeTimeOfMentor(DateTimeHelper.convertToTimestamp(setDate, from), DateTimeHelper.convertToTimestamp(setDate, to),account.getId());
            resp.sendRedirect("/Frog/mentor/schedule?today="+setDate);

        }
        catch (Exception e){
            resp.getWriter().println("error inserted");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String day = req.getParameter("today");
        ArrayList<BookingSchedule> schedules = new ArrayList<>();
        ArrayList<Slot> slots = new ArrayList<>();
        List<Week> weeks = new ArrayList<>();
        MentorDAO mentorDAO = new MentorDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Booking_ScheduleDAO bs = new Booking_ScheduleDAO();
        ArrayList<Booking> bookings = new ArrayList<>();
        if(day == null){
            LocalDate date = LocalDate.now();
            day = DateTimeHelper.convertDateToString(date);
            weeks = DateTimeHelper.getWeekDates(day);
            req.setAttribute("weeks", weeks);
        }else{
             weeks = DateTimeHelper.getWeekDates(day);
            req.setAttribute("weeks", weeks);
        }

        String dayID = req.getParameter("dayID");
        if(dayID!=null){
            ArrayList<BookingSchedule> menteeInfo = new ArrayList<>();
            // menteeInfo = mentorDAO.getInfoByDayID(account.getId(),DateTimeHelper.converDayIDtoStartDate(dayID),DateTimeHelper.converDayIDtoEndDate(dayID));
            req.setAttribute("menteeInfo", menteeInfo);
        }
        String slotID = req.getParameter("slotID");
        if(slotID!=null){
            String [] infoSlotID = slotID.split("_");
            scheduleDAO.insertDayFreeByMentor(account.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));
        }

        slots = scheduleDAO.getSlots();
        schedules = scheduleDAO.getSchedulesByIDnDay(account.getId(), DateTimeHelper.convertStringToDateByDay(weeks.get(0).getDayOfMonth()), DateTimeHelper.convertStringToDateByDay(weeks.get(6).getDayOfMonth()));
        bookings = bs.getBookingByMenteeBookMentor(account.getId());
        req.setAttribute("bookings",bookings);
        req.setAttribute("slots", slots);
        req.setAttribute("count", 1);
        req.setAttribute("mentorID",account.getId());
        req.setAttribute("schedules", schedules);
        req.getRequestDispatcher("/view/mentor/schedule/ViewSchedule.jsp").forward(req, resp);

    }
}
