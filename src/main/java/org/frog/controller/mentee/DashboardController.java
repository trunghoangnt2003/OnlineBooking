package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.ReportDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentee/dashboard")
public class DashboardController extends AuthenticationServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        BookingDAO bookingDAO = new BookingDAO();
        MentorDAO mentorDAO = new MentorDAO();
        String mentee_id = account.getId();

        ArrayList<Mentor> mentorList = mentorDAO.getByBookDone(mentee_id);
        ArrayList<Booking> bookingList = bookingDAO.getHistoryDone(mentee_id);

        request.setAttribute("bookingList", bookingList);
        request.setAttribute("mentorList", mentorList);
        request.getRequestDispatcher("../view/mentee/dashboard/dashboard.jsp").forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
//        BookingDAO bookingDAO = new BookingDAO();
//        MentorDAO mentorDAO = new MentorDAO();
//        String mentee_id ="e8fd47ed-dec2-49bf-829c-b182230ea49d";
//        String mentor_id = request.getParameter("mentor_id");
//
//        boolean isReport = request.getParameter("isReason").equals("reason");
//        if(isReport ){
//            ReportDAO reportDAO = new ReportDAO();
//            String reason = request.getParameter("reason");
//            Report report = new Report();
//
//            Account menter_acc = new Account();
//            menter_acc.setId(mentor_id);
//            Mentor mentor = new Mentor();
//            mentor.setAccount(menter_acc);
//            report.setMentor(mentor);
//
//            Account mentee_acc = new Account();
//            mentee_acc.setId(mentee_id);
//            Mentee mentee = new Mentee();
//            mentee.setAccount(mentee_acc);
//            report.setMentee(mentee);
//
//            report.setReason(reason);
//            reportDAO.insert(report);
//
//            request.setAttribute("success", "success");
//        }
//
//        ArrayList<Mentor> mentorList = mentorDAO.getByBookDone(mentee_id);
//        ArrayList<Booking> bookingList = bookingDAO.getHistoryDone(mentee_id);
//
//        request.setAttribute("bookingList", bookingList);
//        request.setAttribute("mentorList", mentorList);
//        request.getRequestDispatcher("../view/mentee/dashboard/dashboard.jsp").forward(request, response);
    }
}
