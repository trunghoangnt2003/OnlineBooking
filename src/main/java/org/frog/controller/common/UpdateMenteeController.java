package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MenteeDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;

import java.io.IOException;
import java.sql.*;

@WebServlet("/mentee/update")
public class UpdateMenteeController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MenteeDAO menteeDAO = new MenteeDAO();
        // lấy id session
        Mentee mentee = menteeDAO.getMenteeById(account.getId());
        req.setAttribute("mentee", mentee);
        req.getRequestDispatcher("../view/mentee/profile/update.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String dob  = req.getParameter("dob");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String username = req.getParameter("username");
        String gender = req.getParameter("gender");
        System.out.println(id + " " + name + " " + email + " " + phone + " " + address + " " + gender);

        Account a = new Account();
        a.setId(id);
        a.setName(name);
        a.setDob(Date.valueOf((dob)));
        a.setUserName(username);
        a.setPhone(phone);
        a.setAddress(address);
        a.setGender(Integer.parseInt(gender));
        Mentee mentee = new Mentee(a);
        MenteeDAO menteeDAO = new MenteeDAO();
        menteeDAO.updateMentee(mentee);
        resp.sendRedirect("profile");
    }
}
