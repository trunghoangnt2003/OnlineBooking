package org.frog.controller.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.*;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.StatusEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@WebServlet("/admin/manageSchedule")
public class ScheduleManager extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
/*        String[] Schedule = req.getParameterValues("schedule");
        String mentorSchedule = req.getParameter("mentorSchedule");
        int[] id = new int[Schedule.length];

        for (int i = 0; i < Schedule.length; i++) {
            id[i] = Integer.parseInt(Schedule[i]);
        }
        ScheduleDAO scheduleDAO = new ScheduleDAO();

        for (int i = 0; i < id.length; i++) {
            Schedule schedule = scheduleDAO.getScheduleLogs(String.valueOf(id[i]));
            scheduleDAO.insert(schedule);
            scheduleDAO.updateById(id[i], StatusEnum.ACCEPTED);
        }


        resp.sendRedirect("manageSchedule");*/
        fetchPost(req, resp, account);
    }

    private void fetchPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(jsonBuffer.toString(), JsonObject.class);
        Mentor_ScheduleDAO mentor_scheduleDAO = new Mentor_ScheduleDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Booking_ScheduleDAO booking_scheduleDAO = new Booking_ScheduleDAO();
        String MentorSchedule = json.get("mentorSchedule").getAsString();
        String action = json.get("action").getAsString();
        String type = json.get("type").getAsString();
        String message = json.get("message").getAsString();
        int id = Integer.parseInt(MentorSchedule);

        if(type.equals("new")){
            ArrayList<Schedule> schedulesLogs = scheduleDAO.getLogsProcessByMentorScheduleId(id);
            if(action.equals("accept")) {
                //accept
                Date lastDate = schedulesLogs.get(0).getDate();
                Date startDate = schedulesLogs.get(0).getDate();
                for (Schedule schedule : schedulesLogs) {
                    scheduleDAO.insert(schedule);
                    scheduleDAO.updateLogsById(schedule.getId(), StatusEnum.ACCEPTED);
                    Date sdate = schedule.getDate();
                    if(sdate.compareTo(lastDate) > 0) {
                        lastDate = schedule.getDate();
                    }
                    if (sdate.compareTo(startDate) < 0) {
                        startDate = schedule.getDate();
                    }
                }
        Mentor_Schedule mentor_schedule = mentor_scheduleDAO.getById(id);
                if(mentor_schedule.getStart_date() == null ) {
                    mentor_scheduleDAO.updateStartDate(id, startDate);
                }
                if(mentor_schedule.getEnd_date() == null ) {
                    mentor_scheduleDAO.updateEndDate(id, lastDate);

                } else if (mentor_schedule.getStart_date().compareTo(lastDate) > 0) {
                    mentor_scheduleDAO.updateEndDate(id, lastDate);

                }
            }else if(action.equals("reject")) {
                //reject
                mentor_scheduleDAO.updateMessage(id, message);
                for (Schedule schedule : schedulesLogs) {
                    scheduleDAO.updateLogsById(schedule.getId(), StatusEnum.DRAFT);
                }
            }
        } else if (type.equals("remove")) {
            // remove
            ArrayList<Schedule> schedulesLogs = scheduleDAO.getLogsWaitCancelByMentorScheduleId(id);
            if (action.equals("accept")) {
                for (Schedule schedule : schedulesLogs) {
                    scheduleDAO.updateLogsById(schedule.getId(), StatusEnum.CANCEL);
                    Schedule sche = scheduleDAO.getScheduleByInfo(schedule.getMentorSchedule().getId(), schedule.getDate(), schedule.getSlot().getId());
                    ArrayList<BookingSchedule> bookingSchedulesLogs = booking_scheduleDAO.getScheduleBookingLogsByScheduleId(sche.getId());

                    //change booking slot (schedule id) to null if slot deleted has booking in a past
                    for (BookingSchedule bs :bookingSchedulesLogs ){
                        booking_scheduleDAO.changeScheduleIdToZero(bs.getId());
                    }
                    scheduleDAO.deleteSlotAccepted(schedule.getMentorSchedule().getId(), schedule.getDate(), schedule.getSlot().getId());
                }
            } else if (action.equals("reject")) {
                for (Schedule schedule : schedulesLogs) {
                    scheduleDAO.updateLogsById(schedule.getId(), StatusEnum.ACCEPTED);
                }
            }
        }
        resp.sendRedirect("manageSchedule");
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
        Mentor_ScheduleDAO mentor_scheduleDAO = new Mentor_ScheduleDAO();

        int page = 1;
        if (page_raw != null) {
            if (!page_raw.isEmpty()) page = Integer.parseInt(page_raw);
        }

        int total =mentorDAO.getTotalMentor(mentorName);
        int end_page = total / 5;
        if (total % 5 != 0) {
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
        Map<Mentor_Schedule, Map<String, Integer>>  mentorSchedule = mentorDAO.getProcessingSchedule(page,mentorName);
        ArrayList<Slot> slots = slotDAO.selectAll();
        ArrayList<Schedule> schedules = scheduleDAO.getScheduleLogsByMentor(mentorId, from, to);
        ArrayList<Schedule> allSchedule = scheduleDAO.getAllScheduleLogsByMentor(mentorId);
        Mentor_Schedule mentor_schedule = mentor_scheduleDAO.getByMentor(mentorId);



        req.setAttribute("mentor_schedule", mentor_schedule);
        req.setAttribute("allSchedule", allSchedule);
        req.setAttribute("mentorId", mentorId);
        req.setAttribute("name", name);
        req.setAttribute("schedules", schedules);
        req.setAttribute("mentor", mentorName);
        req.setAttribute("total", total);
        req.setAttribute("end_page", end_page);
        req.setAttribute("page", page);
        req.setAttribute("mentorSchedule", mentorSchedule);
        req.setAttribute("week", week);
        req.setAttribute("today", today);
        req.setAttribute("slots", slots);
        req.getRequestDispatcher("../view/manager/manageSchedule.jsp").forward(req, resp);
    }
}
