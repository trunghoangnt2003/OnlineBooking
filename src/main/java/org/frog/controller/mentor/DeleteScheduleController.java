package org.frog.controller.mentor;

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
import org.frog.model.Slot;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.StatusEnum;

import java.io.IOException;
@WebServlet("/mentor/schedule/delete")
public class DeleteScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try{
            String day = req.getParameter("today");
            String slotID = req.getParameter("slotID");
            if(slotID!=null){
                String [] infoSlotID = slotID.split("_");
                ScheduleDAO scheduleDAO = new ScheduleDAO();
                Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
                Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());
                SlotDAO slDAO = new SlotDAO();
                Slot id = slDAO.getTimeSlot(Integer.parseInt(infoSlotID[1]));
                if(DateTimeHelper.compareDayIDtoNow(infoSlotID[0], id.getStart_at(), id.getEnd_at())){
//                    if(scheduleDAO.isSlotAccepted(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]))){
//                        scheduleDAO.deleteSlotAccepted(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));
//                    }
                    if(scheduleDAO.checkProcessStatusScheduleLogs(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]))==StatusEnum.DRAFT){
                        scheduleDAO.deleteDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]), StatusEnum.CANCEL);
                    }
                    else if(scheduleDAO.checkProcessStatusScheduleLogs(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]))==StatusEnum.ACCEPTED){
                        scheduleDAO.deleteDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]),StatusEnum.WAITCANCEL);
                    }else {
                        scheduleDAO.deleteDayFreeByMentor(mentor_schedule.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]),StatusEnum.CANCEL);
                    }
                }
                else{
                    req.getSession().setAttribute("DeleteSlotError", "Can not delete slot in passed");
                }
                resp.sendRedirect("/Frog/mentor/schedule/edit?today="+day);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
