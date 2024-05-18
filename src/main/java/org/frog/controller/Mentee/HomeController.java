package org.frog.controller.Mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.MenterDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.model.Category;
import org.frog.model.Mentor;
import org.frog.model.Skill;

import java.io.*;
import java.util.ArrayList;

@WebServlet("/Home")
public class HomeController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
