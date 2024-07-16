package org.frog.controller.mentor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Mentor_ScheduleDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.DAO.SlotDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor_Schedule;
import org.frog.model.Schedule;
import org.frog.model.Slot;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.StatusEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/mentor/schedule/insert")
public class InsertScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        fetchPost(req,resp,account);
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

        String slotId = json.get("slotID").getAsString();
        String today = json.get("today").getAsString();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        SlotDAO slDAO = new SlotDAO();
        Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
        Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());

        if (slotId != null) {
            String[] infoSlotID = slotId.split("_");

            Slot id = slDAO.getTimeSlot(Integer.parseInt(infoSlotID[1]));
            if (DateTimeHelper.compareDayIDtoNow(infoSlotID[0], id.getStart_at(), id.getEnd_at())) {
                if(scheduleDAO.checkDayExistScheduleLogs(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]))){
                    if(scheduleDAO.checkProcessStatusScheduleLogs(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]))==StatusEnum.WAITCANCEL){
                        scheduleDAO.reMarkDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]),StatusEnum.ACCEPTED);
                    }
                    else{
                        scheduleDAO.reMarkDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]),StatusEnum.DRAFT);
                    }
                }else{
                    scheduleDAO.insertDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));

                }
            } else {
                req.getSession().setAttribute("AddSlotError", "slot in passed");
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
//        ScheduleDAO scheduleDAO = new ScheduleDAO();
//        Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
//        Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());
//        String day = req.getParameter("today");
//        String slotID = req.getParameter("slotID");
//        if (slotID != null) {
//            String[] infoSlotID = slotID.split("_");
//            SlotDAO slDAO = new SlotDAO();
//            Slot id = slDAO.getTimeSlot(Integer.parseInt(infoSlotID[1]));
//            if (DateTimeHelper.compareDayIDtoNow(infoSlotID[0], id.getStart_at(), id.getEnd_at())) {
//                if(scheduleDAO.checkDayExistScheduleLogs(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]))){
//                    scheduleDAO.reMarkDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));
//                }else{
//                    scheduleDAO.insertDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));
//                }
//            } else {
//                req.getSession().setAttribute("AddSlotError", "slot in passed");
//            }
//        }
//        resp.sendRedirect("/Frog/mentor/schedule/edit?today="+day);

    }
}
