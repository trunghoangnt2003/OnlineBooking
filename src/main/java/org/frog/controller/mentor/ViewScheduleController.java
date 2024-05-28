package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Mentor.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.MentorUtils.DateTimeHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
            mentorDAO.updateFreeTimeOfMentor(DateTimeHelper.convertToTimestamp(setDate, from), DateTimeHelper.convertToTimestamp(setDate, to),account.getId());
            resp.sendRedirect("/Frog/mentor/schedule?today="+setDate);

        }
        catch (Exception e){
            resp.getWriter().println("error inserted");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

        String day = req.getParameter("today");
        int[] freetime = {1,2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
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
        String dayID = req.getParameter("dayID");

        MentorDAO mentorDAO = new MentorDAO();
        if(dayID==null){

        }else{
            ArrayList<BookingSchedule> menteeInfo = new ArrayList<>();
            menteeInfo = mentorDAO.getInfoByDayID(account.getId(),DateTimeHelper.converDayIDtoStartDate(dayID),DateTimeHelper.converDayIDtoEndDate(dayID));
            req.setAttribute("menteeInfo", menteeInfo);
        }
        ArrayList<BookingSchedule> bookings = mentorDAO.getBookingbyMentorID(account.getId());
        List<Integer> freetimeList = Arrays.stream(freetime).boxed().collect(Collectors.toList());
        req.setAttribute("freetime", freetimeList);
        req.setAttribute("count", 1);
        schedules = mentorDAO.getScheduleByMentorID(account.getId());
        req.setAttribute("mentorID",account.getId());
        req.setAttribute("schedules", schedules);
        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/view/mentor/schedule/ViewSchedule.jsp").forward(req, resp);

    }
}
