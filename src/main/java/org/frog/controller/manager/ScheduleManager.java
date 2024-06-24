package org.frog.controller.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.DAO.SlotDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor;
import org.frog.model.Schedule;
import org.frog.model.Slot;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@WebServlet("/admin/manageSchedule")
public class ScheduleManager extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String ymd_raw = req.getParameter("ymd");
        String page_raw = req.getParameter("page");
        String mentorName = req.getParameter("mentor");
        String mentorId = req.getParameter("mentorId");
        String name = req.getParameter("name");
        SlotDAO slotDAO = new SlotDAO();
        MentorDAO mentorDAO = new MentorDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();



        int page = 1;
        if (page_raw != null) {
            if (!page_raw.isEmpty()) page = Integer.parseInt(page_raw);
        }

        int total =mentorDAO.getTotalMentor(mentorName);
        int end_page = total / 4;
        if (total % 4 != 0) {
            end_page++;
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
        Map<Mentor, Integer>  mentors = mentorDAO.getProcessingSchedule(page,mentorName);
        ArrayList<Slot> slots = slotDAO.selectAll();
        ArrayList<Schedule> schedules = scheduleDAO.getScheduleLogsByMentor(mentorId, from, to);


        req.setAttribute("mentorId", mentorId);
        req.setAttribute("name", name);
        req.setAttribute("schedules", schedules);
        req.setAttribute("mentor", mentorName);
        req.setAttribute("total", total);
        req.setAttribute("end_page", end_page);
        req.setAttribute("page", page);
        req.setAttribute("mentors", mentors);
        req.setAttribute("week", week);
        req.setAttribute("today", today);
        req.setAttribute("slots", slots);
        req.getRequestDispatcher("../view/manager/manageSchedule.jsp").forward(req, resp);
    }
}
