package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;
@WebServlet("/mentor/schedule/confirm")
public class ConfirmScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try{
            String id = req.getParameter("ID").trim();
            String opt = req.getParameter("option");
            String manage = req.getParameter("manage");
            Booking_ScheduleDAO bsDAO = new Booking_ScheduleDAO();
            if(!id.isEmpty() || !opt.isEmpty()){
                if(opt.equals("present")){
                    bsDAO.updateBookingDetail(Integer.parseInt(id),true);
                }else if(opt.equals("absent")){
                    bsDAO.updateBookingDetail(Integer.parseInt(id),false);
                }
            }
            if(manage != null ){
                resp.sendRedirect("/Frog/mentor/schedule/manage");
            }else{
                resp.sendRedirect("/Frog/mentor/schedule");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
