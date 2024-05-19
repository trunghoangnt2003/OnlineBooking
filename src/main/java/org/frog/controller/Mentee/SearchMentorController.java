package org.frog.controller.Mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.MenterDAO;
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
        CategoryDAO categoryDAO = new CategoryDAO();
        SkillsDAO skillsDAO = new SkillsDAO();
        MenterDAO menterDAO = new MenterDAO();

        ArrayList<Category> list_cate = categoryDAO.selectAll();
        ArrayList<Skill> list_skill = skillsDAO.getAll();
        ArrayList<Mentor> list_mentor = menterDAO.selectAll();



        request.setAttribute("list_mentor", list_mentor);
        request.setAttribute("list_cate", list_cate);
        request.setAttribute("list_skill", list_skill);
        request.getRequestDispatcher("view/mentee/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
