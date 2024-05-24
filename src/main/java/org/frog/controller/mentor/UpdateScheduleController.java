package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Mentor.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.BookingSchedule;
import org.frog.model.Schedule;
import org.frog.utility.MentorUtils.DateTimeHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

@WebServlet("/mentor/schedule/update")
public class UpdateScheduleController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

        try {
            String opt = req.getParameter("option");
            String start = req.getParameter("start");
            String end = req.getParameter("end");
            String id = req.getParameter("id");
            String menteeID = req.getParameter("menteeID");
            if(start == null && end == null && id == null && opt == null ){
                req.setAttribute("errorNull", "the data is null please enter data again");
                req.getRequestDispatcher("/view/mentor/schedule/ViewSchedule.jsp").forward(req, resp);
            }else{
                if(opt.equals("true")){
                    Timestamp startT = Timestamp.valueOf(start);
                    Timestamp endT = Timestamp.valueOf(end);
                    MentorDAO mentorDAO = new MentorDAO();
                    BookingSchedule bs = new BookingSchedule();
                    bs = mentorDAO.getBookNScheID(startT, endT, id,menteeID);
                    if(bs.getSchedule().getStatus() == true){
                        mentorDAO.updateBusyMentorSchedule(bs.getSchedule().getId());
                        mentorDAO.updateResultsForMenteeRequest(bs.getBooking().getId(),3);
                        req.getSession().setAttribute("existError", "");

                        resp.sendRedirect("/Frog/mentor/schedule");
                    }
                    else{
                        int bookingID = bs.getSchedule().getId();
                        ArrayList<Schedule> menteeBookedTime = new ArrayList<>();
                        menteeBookedTime = mentorDAO.getBookedScheduleMentee(id,bookingID);
                       boolean check = DateTimeHelper.compareTimeMenteeBook(menteeBookedTime,startT,endT);
                       if(check == true){
                           mentorDAO.updateResultsForMenteeRequest(bs.getBooking().getId(),3);
                           req.getSession().setAttribute("existError", "");

                           resp.sendRedirect("/Frog/mentor/schedule");
                       }
                       else{
                           req.getSession().setAttribute("existError", "This time is not free. Please add free time or reject.");
                           resp.sendRedirect("/Frog/mentor/schedule");


                       }
                    }

                }
               else{
                    Timestamp startT = Timestamp.valueOf(start);
                    Timestamp endT = Timestamp.valueOf(end);
                    MentorDAO mentorDAO = new MentorDAO();
                    BookingSchedule bs = new BookingSchedule();
                    bs = mentorDAO.getBookNScheID(startT, endT, id,menteeID);
                    mentorDAO.updateResultsForMenteeRequest(bs.getBooking().getId(),2);
                    resp.sendRedirect("/Frog/mentor/schedule");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
