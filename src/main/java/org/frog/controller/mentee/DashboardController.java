package org.frog.controller.mentee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import java.util.Map;

@WebServlet("/mentee/dashboard")
public class DashboardController extends AuthenticationServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        BookingDAO bookingDAO = new BookingDAO();
        MentorDAO mentorDAO = new MentorDAO();
        String mentee_id = account.getId();

        ArrayList<Mentor> mentorList = mentorDAO.getByBookDone(mentee_id);
        ArrayList<Booking> bookingList = bookingDAO.getHistoryDone(mentee_id);
        Map<String,Integer> statistic = bookingDAO.statisticByMentee(mentee_id);
        Map<String,Integer> total = bookingDAO.getTotalBookByMentee(mentee_id);


        request.setAttribute("bookingList", bookingList);
        request.setAttribute("mentorList", mentorList);
        request.getRequestDispatcher("../view/mentee/dashboard/dashboard.jsp").forward(request, response);
    }

    private void sendFetch(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        BookingDAO bookingDAO = new BookingDAO();
        String mentee_id = account.getId();
        Gson gson = new Gson();
        try {
            Map<String, Integer> statistic = bookingDAO.statisticByMentee(mentee_id);
            Map<String, Integer> total = bookingDAO.getTotalBookByMentee(mentee_id);


            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("status", "success");
            
            JsonObject statisticJson = gson.toJsonTree(statistic).getAsJsonObject();
            jsonResponse.add("statistic", statisticJson);


            JsonObject totalJson = gson.toJsonTree(total).getAsJsonObject();
            jsonResponse.add("total", totalJson);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", e.getMessage());
            response.getWriter().write(gson.toJson(jsonResponse));
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        sendFetch(request, response, account);
    }
}
