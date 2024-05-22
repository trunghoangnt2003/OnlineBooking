package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MenteeDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;

import java.io.IOException;

@WebServlet("/mentee/profile")
public class MenteeController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MenteeDAO menteeDAO = new MenteeDAO();
        Mentee mentee = menteeDAO.getMenteeById(account.getId());
        req.setAttribute("mentee", mentee);
        req.getRequestDispatcher("../view/mentee/profile/profile.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
