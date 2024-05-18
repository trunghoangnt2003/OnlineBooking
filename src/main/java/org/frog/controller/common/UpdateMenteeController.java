package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MenteeDAO;
import org.frog.model.Account;
import org.frog.model.Mentee;

import java.io.IOException;

@WebServlet("/update")
public class UpdateMenteeController extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        MenteeDAO menteeDAO = new MenteeDAO();
        String id = req.getParameter("id");

        // lấy id session
        Mentee mentee = menteeDAO.getMenteeById("e8fd47ed-dec2-49bf-829c-b182230ea49d");
        req.setAttribute("mentee", mentee);
        req.getRequestDispatcher("mentee/profile/update.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String gender = req.getParameter("gender");

        Account a = new Account();
        a.setId(id);
        a.setName(name);
        a.setEmail(email);
        a.setPhone(phone);
        a.setAddress(address);
        a.setGender(gender);
        Mentee mentee = new Mentee(a);
        MenteeDAO menteeDAO = new MenteeDAO();
        menteeDAO.updateMentee(mentee);


    }
}
