package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Level_Skills;
import org.frog.model.Mentor;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/view_profile")
public class ViewProfileController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            MentorDAO mentorDAO = new MentorDAO();
            Mentor mentor = mentorDAO.getMentorById(account.getId());
            if ( mentor == null) {
                resp.sendRedirect("/Frog/mentor/create_profile");
            }else {
                Level_SkillDAO level_skillDAO = new Level_SkillDAO();
                ArrayList<Level_Skills> level_skills = level_skillDAO.getLevel_SkillByMentorId(account.getId());

                req.setAttribute("level_skills", level_skills);
                req.setAttribute("mentor", mentor);
                req.getRequestDispatcher("../view/mentor/view_profile.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
