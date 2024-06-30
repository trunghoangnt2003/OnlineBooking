package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Mentor_ScheduleDAO;
import org.frog.DAO.ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor_Schedule;
import org.frog.model.Week;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mentor/schedule/set")
public class SetScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            String upWeek = req.getParameter("week");
            String slotData = req.getParameter("data");
            ScheduleDAO scheduleDAO = new ScheduleDAO();
            Mentor_ScheduleDAO mentor_scheduleDao = new Mentor_ScheduleDAO();
            Mentor_Schedule mentor_schedule = mentor_scheduleDao.getByMentor(account.getId());

            int weekNum = Integer.parseInt(upWeek);
            List<Week> weeks = new ArrayList<>();
            int numberUpdateSuccess = 0;
            String [] slots = slotData.split(",");
            for (int i = 1; i <= weekNum; i++) {
                weeks.addAll(DateTimeHelper.getWeekDates(DateTimeHelper.getFutureDate(i)));
            }
            for (String slot : slots) {
                for(Week week : weeks){
                    String[] slotInfo = slot.split("_");
                    if(slotInfo[0].equals(week.getDayOfWeek())){
                        for (int i = 1; i < slotInfo.length; i++) {
                            numberUpdateSuccess += scheduleDAO.insertDayFreeByMentor(mentor_schedule.getId(),DateTimeHelper.convertStringToDateByDay(week.getDayOfMonth()),Integer.parseInt(slotInfo[i]));
                        }
                    }
                }
            }
            req.getSession().setAttribute("numberUpdateSuccess", numberUpdateSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/Frog/mentor/schedule/edit");

    }
}
