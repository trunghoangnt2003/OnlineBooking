package org.frog.controller.Mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.model.Mentor;
import org.frog.model.Skill;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/SearchMentor")
public class SearchMentorController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        String skill_name = request.getParameter("skill");
        String level = request.getParameter("level");

        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        System.out.println(page);

        MentorDAO mentorDAO = new MentorDAO();
        SkillsDAO skillsDAO = new SkillsDAO();

        ArrayList<Mentor> list_mentor = mentorDAO.getMentorAndPaging(page, skill_name, level);


        int totalMentor = mentorDAO.totalMentor(skill_name, level);
        int end_page = totalMentor / 4;
        if (mentorDAO.totalMentor(skill_name, level) % 4 != 0) {
            end_page++;
        }

        Skill skill = skillsDAO.getByName(skill_name);

        request.setAttribute("skill_name", skill_name);
        request.setAttribute("total_mentor", totalMentor);
        request.setAttribute("end_page", end_page);
        request.setAttribute("page", page);
        request.setAttribute("skill", skill);
        request.setAttribute("level", level);
        request.setAttribute("list_mentor", list_mentor);
        request.getRequestDispatcher("view/mentee/search_mentor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
