package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.DAO.ScheduleDAO;
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
            String bookId = null;
            String actionId = null;
            String bookIdRaw = req.getParameter("dataSlot");
            String actionIdRaw = req.getParameter("dataSlot");
            if(manage!=null){
                 bookId = bookIdRaw.split("_")[0];
                 actionId = actionIdRaw.split("_")[1];
            }
            Booking_ScheduleDAO bsDAO = new Booking_ScheduleDAO();
//            ScheduleDAO scheDAO = new ScheduleDAO();
//            BookingDAO bDAO = new BookingDAO();
            if(!id.isEmpty() && !opt.isEmpty()){
                if(opt.equals("present")){
                    bsDAO.updateBookingDetail(Integer.parseInt(id),true);
                }else if(opt.equals("absent")){
                    bsDAO.updateBookingDetail(Integer.parseInt(id),false);
                }
                // auto send mail
//              if(bDAO.findBookingIdByBookingScheduleId(Integer.parseInt(id)) != 0){
//                  int bookingId = bDAO.findBookingIdByBookingScheduleId(Integer.parseInt(id));
//                  if(scheDAO.checkLastSlot(bookingId)){
//                      String menteeId = bDAO.getBookingById(bookingId).getMentee().getAccount().getId();
//                      resp.sendRedirect("/Frog/confirmMail?menteeId=" + menteeId + "&bookingId=" + bookingId +"&isLastSlot=true&manage="+manage+"&bookId="+bookId+"&actionId="+actionId);
//                  }
//              }

            }
            if(manage != null ){
                resp.sendRedirect("/Frog/mentor/schedule/manage?id="+bookId+"&action="+actionId);
            }else{
                resp.sendRedirect("/Frog/mentor/schedule");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
