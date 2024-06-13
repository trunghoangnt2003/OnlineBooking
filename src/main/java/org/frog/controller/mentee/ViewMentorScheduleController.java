package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.*;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.StatusEnum;

import javax.mail.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mentee/booking-schedule")
public class ViewMentorScheduleController extends AuthenticationServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response,Account account) throws ServletException, IOException {
        String ymd_raw = request.getParameter("ymd");
        String mentor_id = request.getParameter("mentorId"); // get mentor by id day qua lai giua controller va jsp
        String skill = request.getParameter("skill");
        String level = request.getParameter("level");
        String bookings_raw = request.getParameter("bookId");

        SlotDAO slotDAO = new SlotDAO();
        Booking_ScheduleDAO booking_scheduleDAO = new Booking_ScheduleDAO();
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Slot> slots = slotDAO.selectAll();
        Level_SkillDAO level_skillDAO = new Level_SkillDAO();

        int bookId = 0;
        if (bookings_raw != null) {
            bookId = Integer.parseInt(bookings_raw);
        }
        String mentee_id = account.getId();
        String[] bookings = request.getParameterValues("booking-schedule");

        ArrayList<BookingSchedule> bookingLogs = booking_scheduleDAO.getLogs(bookId);
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

        if(bookingLogs != null) {
            bookingList.addAll(bookingLogs);
            ymd_raw = bookingLogs.get(0).getSchedule().getDate().toString();
        }

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
        Mentor mentor = mentorDAO.getMentorById(mentor_id);
        Level_Skills level_skills = level_skillDAO.getBySkillAndLevel(skill, level);


        request.setAttribute("level_skills", level_skills);
        request.setAttribute("mentor", mentor);
        request.setAttribute("bookingList", bookingList);
        request.setAttribute("mentee_id", mentee_id);
        request.setAttribute("bookingSchedules", bookingSchedules);
        request.setAttribute("week", week);
        request.setAttribute("today", today);
        request.setAttribute("slots", slots);
        request.getRequestDispatcher("../view/mentee/schedule/view_mentor_schedule.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException{

        String[] bookings = request.getParameterValues("booking-schedule");
        String mentor_id = request.getParameter("mentorId"); // get mentor by id day qua lai giua controller va jsp
        String skill = request.getParameter("skill");
        String level = request.getParameter("level");
        String amount_raw = request.getParameter("total_amount");
        String description = request.getParameter("message");

        ArrayList<Integer> scheduleList = new ArrayList<>();
        ArrayList<Integer> slotList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        int amount =0;
        if(amount_raw != null) {
            amount = Integer.parseInt(amount_raw);
        }
        if(bookings != null) {
            for (String booking : bookings) {
                try {
                    String[] array_bookings = booking.split("_");
                    scheduleList.add(Integer.parseInt(array_bookings[0]));
                    slotList.add(Integer.parseInt(array_bookings[1]));
                    dateList.add(array_bookings[2]);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }

        BookingDAO bookingDAO = new BookingDAO();
        Level_SkillDAO level_skillDAO = new Level_SkillDAO();
        Booking_ScheduleDAO booking_scheduleDAO = new Booking_ScheduleDAO();


        Booking booking = new Booking();

        Status status = new Status();
        status.setId(StatusEnum.PROCESSING);
        booking.setStatus(status);

        booking.setAmount(amount);

        booking.setDescription(description);

        Mentor mentor = new Mentor();
        Account acc_mentor = new Account();
        acc_mentor.setId(mentor_id);
        mentor.setAccount(acc_mentor);
        booking.setMentor(mentor);

        Mentee mentee = new Mentee();
        mentee.setAccount(account);
        booking.setMentee(mentee);

        booking.setStartDate(java.sql.Date.valueOf(dateList.get(0)));
        booking.setEndDate(java.sql.Date.valueOf(dateList.get(dateList.size() - 1)));

        Level_Skills level_skills = level_skillDAO.getBySkillAndLevel(skill, level);
        booking.setLevel_skills(level_skills);

        bookingDAO.insertBooking(booking);

        Booking book = bookingDAO.findByInfo(booking);


        booking_scheduleDAO.makeBooking_Schedule(book, scheduleList);

        response.sendRedirect("../mentee/viewBooking");
    }




}