package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/manageCV")
public class ManageCVMentor extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Mentor> mentors = mentorDAO.getAllMentor();

        req.setAttribute("mentors", mentors);
        req.setAttribute("account", account);
        System.out.println();
        req.getRequestDispatcher("../view/admin/manageCV.jsp").forward(req, resp);
    }
}
