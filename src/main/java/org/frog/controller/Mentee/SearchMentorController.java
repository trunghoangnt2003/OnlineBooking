package org.frog.controller.Mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Category;
import org.frog.model.Mentor;
import org.frog.model.Skill;

import java.io.*;
import java.util.ArrayList;

@WebServlet("/SearchMentor")
public class SearchMentorController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        System.out.println(page);

        CategoryDAO categoryDAO = new CategoryDAO();
        SkillsDAO skillsDAO = new SkillsDAO();
        MentorDAO menterDAO = new MentorDAO();

        ArrayList<Category> list_cate = categoryDAO.selectAll();
        ArrayList<Skill> list_skill = skillsDAO.getAll();
        ArrayList<Mentor> list_mentor = menterDAO.getMentorAndPaging(page);
        int end_page = menterDAO.totalMentor() / 3;
        if (menterDAO.totalMentor() % 3 != 0) {
            end_page++;
        }

        System.out.println("sada"+end_page);
        request.setAttribute("end_page", end_page);
        request.setAttribute("page", page);
        request.setAttribute("list_mentor", list_mentor);
        request.setAttribute("list_cate", list_cate);
        request.setAttribute("list_skill", list_skill);
        request.getRequestDispatcher("view/mentee/search_mentor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
