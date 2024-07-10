package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/Home")
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Mentor> mentors = mentorDAO.getAllMentorFollowRate();

        req.setAttribute("mentor", mentors);
        req.getRequestDispatcher("view/common/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
