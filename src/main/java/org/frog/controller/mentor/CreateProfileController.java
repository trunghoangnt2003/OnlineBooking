package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Category;
import org.frog.model.Mentor;
import org.frog.model.Skill;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/create_profile")
public class CreateProfileController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String edu = req.getParameter("edu");
        String exp = req.getParameter("exp");
        String raw_price = req.getParameter("price");
        String detail = req.getParameter("detail");
        int price = 0;
        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = new Mentor();
        if(raw_price != null) {
            try {
                price = Integer.parseInt(raw_price);
            }catch (NumberFormatException e) {
                req.getRequestDispatcher("/mentor/create_profile").forward(req, resp);
                return;
            }
        }
        mentor.setEducation(edu);
        mentor.setExperience(exp);
        mentor.setAccount(account);
        mentor.setPrice(price);
        mentor.setProfileDetail(detail);
        mentorDAO.update(mentor);
        resp.sendRedirect("/Frog/mentor/view_profile");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            AccountDAO accountDAO = new AccountDAO();
            account = accountDAO.getAccountById(account.getId());

            SkillsDAO skillDAO = new SkillsDAO();
            ArrayList<Skill> skills = skillDAO.getSkillByCateId(3);

            CategoryDAO categoryDAO = new CategoryDAO();
            ArrayList<Category> cate = categoryDAO.getAll();

            req.setAttribute("skill", skills);
            req.setAttribute("cate", cate);
            req.setAttribute("account", account);
            req.getRequestDispatcher("../view/mentor/create_profile.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
