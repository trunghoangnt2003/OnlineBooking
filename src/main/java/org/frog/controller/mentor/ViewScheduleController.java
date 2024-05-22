package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Mentor.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.model.Schedule;
import org.frog.model.Week;
import org.frog.utility.MentorUtils.DateTimeHelper;

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
            mentorDAO.updateFreeTimeOfMentor(DateTimeHelper.convertToTimestamp(setDate, from), DateTimeHelper.convertToTimestamp(setDate, to), "87928f9e-c513-4eea-9c29-0c403c57b537");
            resp.sendRedirect("/prog/mentor/schedule?today="+setDate);

        }
        catch (Exception e){
            resp.getWriter().println("error inserted");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

        String day = req.getParameter("today");
        int[] freetime = {1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        ArrayList<Schedule> schedules = new ArrayList<>();
        if(day == null){
            LocalDate date = LocalDate.now();
            day = DateTimeHelper.convertDateToString(date);
            List<Week> weeks = DateTimeHelper.getWeekDates(day);
            req.setAttribute("weeks", weeks);
        }else{
            List<Week> weeks = DateTimeHelper.getWeekDates(day);
            req.setAttribute("weeks", weeks);
        }

        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Booking> bookings = mentorDAO.getBookingbyMentorID("87928f9e-c513-4eea-9c29-0c403c57b537");
        List<Integer> freetimeList = Arrays.stream(freetime).boxed().collect(Collectors.toList());
        req.setAttribute("freetime", freetimeList);
        req.setAttribute("count", 0);
        schedules = mentorDAO.getScheduleByMentorID("87928f9e-c513-4eea-9c29-0c403c57b537");
        req.setAttribute("schedules", schedules);
        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/view/mentor/schedule/ViewSchedule.jsp").forward(req, resp);

    }
}
