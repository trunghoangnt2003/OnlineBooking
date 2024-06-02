package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.MentorDAO;
import org.frog.model.Mentor;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/Home")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Mentor> mentors = mentorDAO.getAllMentorFollowRate();

        request.setAttribute("mentor", mentors);
        request.getRequestDispatcher("view/common/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
