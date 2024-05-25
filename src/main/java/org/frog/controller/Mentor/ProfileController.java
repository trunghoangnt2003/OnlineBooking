package org.frog.controller.Mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.SkillDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Level_Skills;
import org.frog.model.Mentor;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/profile")
public class ProfileController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            MentorDAO mentorDAO = new MentorDAO();
            Mentor mentor = mentorDAO.getMentorById(account.getId());
            if ( mentor == null) {
                resp.sendRedirect("/Frog/mentor/createcv");
            }else {
                Level_SkillDAO level_skillDAO = new Level_SkillDAO();
                System.out.println("mentor id :" +account.getId());
                ArrayList<Level_Skills> level_skills = level_skillDAO.getLevel_SkillByMentorId(account.getId());

                for (Level_Skills level_skill : level_skills) {
                    System.out.println(level_skill.getLevel().getName());
                    System.out.println(level_skill.getSkill().getName());
                }
                req.setAttribute("level_skills", level_skills);
                req.setAttribute("mentor", mentor);
                req.getRequestDispatcher("../view/mentor/profile.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
