package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MenteeDAO;
import org.frog.model.Mentee;

import java.io.IOException;

@WebServlet("/profile")
public class MenteeController extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        MenteeDAO menteeDAO = new MenteeDAO();
        Mentee mentee = menteeDAO.getMenteeById("e8fd47ed-dec2-49bf-829c-b182230ea49d");
        req.setAttribute("mentee", mentee);
        req.getRequestDispatcher("mentee/profile/profile.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
