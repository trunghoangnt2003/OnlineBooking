package org.frog.controller.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.ReportDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Administrator;
import org.frog.model.Report;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/report")
public class ReportManager extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        String action = request.getParameter("action");
        String report_id_raw = request.getParameter("report_id");
        ReportDAO reportDAO = new ReportDAO();
        if (action != null) {
            if (!action.isEmpty()) {
                if (action.equals("accept")) {
                    if (report_id_raw != null) {
                        if (!report_id_raw.isEmpty()) {
                            int report_id = Integer.parseInt(report_id_raw);
                            Report reportMentor = reportDAO.getReportAndMentorById(report_id);
                            //chuyenr trang thái
                            Report report = new Report();
                            report.setId(report_id);
                            Administrator addmin = new Administrator();
                            addmin.setAccount(account);
                            report.setAdministrator(addmin);
                            reportDAO.updateReport(report);
                            response.sendRedirect("../admin/mentor?name=" + reportMentor.getMentor().getAccount().getUserName());
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        String mentee = request.getParameter("mentee");
        String mentor = request.getParameter("mentor");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String status_raw = request.getParameter("status");

        if(mentee == null) mentee = "";
        if(mentor == null) mentor = "";
        if(from == null) from = "";
        if(to == null) to = "";
        if(status_raw == null) status_raw = "0";
        int page = 1;

        if (page_raw != null) {
            if (!page_raw.isEmpty()) page = Integer.parseInt(page_raw);
        }
        ReportDAO reportDAO = new ReportDAO();
        ArrayList<Report> reports = reportDAO.getAllProcessing(page, mentee, mentor, from, to, status_raw);
        int total = reportDAO.total(mentee, mentor, from, to, status_raw);
        int end_page = total / 4;
        if (total % 4 != 0) {
            end_page++;
        }


        request.setAttribute("mentee", mentee);
        request.setAttribute("mentor", mentor);
        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute("status", status_raw);
        request.setAttribute("end_page", end_page);
        request.setAttribute("page", page);
        request.setAttribute("total", total);
        request.setAttribute("reports", reports);
        request.getRequestDispatcher("../view/manager/reportManager.jsp").forward(request, response);
    }
}
