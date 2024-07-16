package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.Mentor_CV_LogDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Level_Skills;
import org.frog.model.Mentor_CV_Log;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

@WebServlet("/admin/manageCV")
public class ManageCVMentor extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String action = req.getParameter("action");
        String accountId = req.getParameter("accountId");

        Mentor_CV_LogDAO mentor_cv_logDAO = new Mentor_CV_LogDAO();
        if ("APPROVE".equals(action)) {
            mentor_cv_logDAO.updateStatus(accountId, 14);
            MentorDAO mentorDAO = new MentorDAO();
            Mentor_CV_Log mentor_cv_log = mentor_cv_logDAO.getMentorCVLogByAccountId(accountId);
            mentorDAO.updateMentor(mentor_cv_log);
        } else if ("REJECT".equals(action)) {
            mentor_cv_logDAO.updateStatus(accountId, 2);
        }
        resp.sendRedirect("/Frog/admin/manageCV");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Mentor_CV_Log> mentors = mentorDAO.getAllMentor();

        Level_SkillDAO level_skillDAO = new Level_SkillDAO();
        for (Mentor_CV_Log mentor : mentors) {
            ArrayList<Level_Skills> level_skills = level_skillDAO.getLevel_SkillByMentorId(mentor.getAccount().getId());
            mentor.setLevel_skills(level_skills);
        }

//        String page_raw = req.getParameter("page");
//        String name = req.getParameter("name");
//        if(name == null || name.isEmpty()) name = "";
//        int page = 1;
//        if (page_raw != null) {
//            page = Integer.parseInt(page_raw);
//        }
//        ArrayList<Mentor> list_mentor = mentorDAO.getMentorAndPagingAndSearch(page,name);
//        int totalMentor = mentorDAO.totalMentorAndPagingAndSearch(page,name);
//        int end_page = totalMentor /4 ;
//        if ( totalMentor % 4 != 0) {
//            end_page++;
//        }
//        req.setAttribute("name",name);
//        req.setAttribute("end_page",end_page);
//        req.setAttribute("list",list_mentor);
//        req.setAttribute("page",page);

        req.setAttribute("totalMentor", mentors.size());
        req.setAttribute("mentors", mentors);
        req.setAttribute("account", account);
        req.getRequestDispatcher("../view/admin/manageCV.jsp").forward(req, resp);
    }
}
