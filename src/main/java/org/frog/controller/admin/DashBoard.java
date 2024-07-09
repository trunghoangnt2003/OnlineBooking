package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.BookingDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.utility.JsonUtils;
import org.frog.utility.StatusEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.*;

@WebServlet("/admin/dash")
public class DashBoard extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String action = req.getParameter("action");
        BookingDAO bookingDAO = new BookingDAO();
        Map<String, Object> responseData = new HashMap<>();
        if(action.equals("booking")){
            int statusAcp = bookingDAO.countBookingByStatus(StatusEnum.ACCEPTED) + bookingDAO.countBookingByStatus(StatusEnum.DONE);
            int statusReject = bookingDAO.countBookingByStatus(StatusEnum.REJECT) + bookingDAO.countBookingByStatus(StatusEnum.CANCEL);
            float acp = 1.0f*statusAcp/(statusReject+statusAcp);
            float reject = 1.0f*statusReject/(statusReject+statusAcp);

            responseData.put("bookingReject", reject);
            responseData.put("bookingAccepted", acp);
        }else if(action.equals("income")){
            ArrayList<Booking> estimatedIncome = bookingDAO.estimatedIncome("","");
            responseData.put("estimatedIncome", estimatedIncome);
        }else if(action.equals("account")){
            AccountDAO accountDAO = new AccountDAO();
            int mentee = accountDAO.getTotalMentee();
            int mentor = accountDAO.getTotalMentor();
            int manager = accountDAO.getTotalManager();
            float total = 1.0f * mentee + mentor +manager;
            responseData.put("mentee", 1.0f*mentee/total);
            responseData.put("mentor", 1.0f*mentor/total);
            responseData.put("manager", 1.0f*manager/total);
            responseData.put("count_mentee", mentee);
            responseData.put("count_mentor", mentor);
            responseData.put("count_manager", manager);
        }else if (action.equals("skill")){
            SkillsDAO skillsDAO = new SkillsDAO();
            Map<String, Integer> list = skillsDAO.getSkill();
            responseData.put("skill",list);
        }

        String jsonResponse = JsonUtils.toJson(responseData);
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        BookingDAO bookingDAO = new BookingDAO();
        ArrayList<Booking> estimatedIncome = bookingDAO.estimatedIncome("","");
        float total = 0;
        for (Booking booking : estimatedIncome) {
                total += booking.getAmount();
        }
        Currency vnd = Currency.getInstance("VND");
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vndFormatter = NumberFormat.getCurrencyInstance(localeVN);

        vndFormatter.setCurrency(vnd);
        req.setAttribute("total", vndFormatter.format(total));
        req.getRequestDispatcher("../view/admin/dash.jsp").forward(req, resp);
    }

}
