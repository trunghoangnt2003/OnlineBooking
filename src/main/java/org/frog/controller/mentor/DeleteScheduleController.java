package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.utility.DateTimeHelper;

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
                scheduleDAO.deleteDayFreeByMentor(account.getId(), DateTimeHelper.convertStringToDateByDay(infoSlotID[0]), Integer.parseInt(infoSlotID[1]));
                resp.sendRedirect("/Frog/mentor/schedule?today="+day);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
