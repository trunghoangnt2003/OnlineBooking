package org.frog.controller.Mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.ScheduleDAO;
import org.frog.DAO.SlotDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Slot;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
@WebServlet("/mentor/schedule/insert")
public class InsertScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        String day = req.getParameter("today");
        String slotID = req.getParameter("slotID");
        if (slotID != null) {
            String[] infoSlotID = slotID.split("_");
            SlotDAO slDAO = new SlotDAO();
            Slot id = slDAO.getTimeSlot(Integer.parseInt(infoSlotID[1]));
            if (DateTimeHelper.compareDayIDtoNow(infoSlotID[0], id.getStart_at(), id.getEnd_at())) {
                scheduleDAO.insertDayFreeByMentor(account.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));

            } else {
                req.getSession().setAttribute("AddSlotError", "slot in passed");
            }
        }
        resp.sendRedirect("/Frog/mentor/schedule?today="+day);

    }
}
