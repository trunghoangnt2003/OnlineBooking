package org.frog.controller.Mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.model.Mentor;

import java.io.IOException;

@WebServlet("/mentor/home")
public class HomeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MentorDAO mentorDAO = new MentorDAO();
            Mentor mentor = mentorDAO.getMentorById(req.getParameter("id"));
            req.setAttribute("mentor", mentor);
            req.getRequestDispatcher("../view/mentor/home_controller.jsp").forward(req, resp);
        }catch (ServletException | IOException e){
            throw new RuntimeException(e);
        }
    }
}
