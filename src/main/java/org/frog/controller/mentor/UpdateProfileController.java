package org.frog.controller.mentor;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor;

import java.io.IOException;

@WebServlet("/mentor/update_profile")
public class UpdateProfileController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String raw_dob = req.getParameter("dob");
        String gender = req.getParameter("gender");

        String profile_detail = req.getParameter("detail");
        String education = req.getParameter("edu");
        String experience = req.getParameter("exp");
        String raw_price = req.getParameter("price");

        AccountDAO accountDAO = new AccountDAO();
        account.setName(name);
        account.setAddress(address);
        account.setPhone(phone);
        //account.setGender(Integer.parseInt(gender));
        accountDAO.updateAccount(account);

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = new Mentor();
        mentor.setProfileDetail(profile_detail);
        mentor.setEducation(education);
        mentor.setExperience(experience);
        //mentor.setPrice(Integer.parseInt(raw_price));
        mentorDAO.updateMentor(mentor, account.getId());

        resp.sendRedirect("view_profile");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentorById(account.getId());
        System.out.println(mentor.getAccount().getGender());
        req.setAttribute("mentor", mentor);
        req.getRequestDispatcher("../view/mentor/update_profile.jsp").forward(req, resp);
    }
}
