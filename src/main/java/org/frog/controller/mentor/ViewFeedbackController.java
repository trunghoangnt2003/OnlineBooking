package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.ReviewDAO;
import org.frog.model.Mentor;
import org.frog.model.Review;


import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/feedback")
public class ViewFeedbackController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("mentorid");
        ReviewDAO reviewDAO = new ReviewDAO();
        ArrayList<Review> reviews = reviewDAO.getAllReview(id);
        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentorById(id);

        req.setAttribute("mentor", mentor);
        req.setAttribute("reviews", reviews);
        req.setAttribute("id", id);
        req.getRequestDispatcher("../view/mentor/feedback.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
