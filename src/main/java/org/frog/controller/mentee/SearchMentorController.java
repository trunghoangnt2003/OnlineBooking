package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.DAO.WishListDAO;
import org.frog.model.Account;
import org.frog.model.Mentor;
import org.frog.model.Skill;
import org.frog.model.WishList;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/SearchMentor")
public class SearchMentorController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        String skill_name = request.getParameter("skill");
        String level = request.getParameter("level");
        String search = request.getParameter("search");
        String order_raw = request.getParameter("order");

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String mentee_id = "";
        if (account != null) {
            mentee_id = account.getId();
        }
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }


        MentorDAO mentorDAO = new MentorDAO();
        SkillsDAO skillsDAO = new SkillsDAO();
        WishListDAO wishListDAO = new WishListDAO();
        int order = 0;
        if(order_raw != null){
            order = Integer.parseInt(order_raw);
        }

        ArrayList<Mentor> list_mentor = mentorDAO.getMentorAndPaging(page, skill_name, level,search,order);
        int totalMentor = mentorDAO.totalMentor(skill_name, level,search);
        int end_page = totalMentor / 4;
        if (totalMentor % 4 != 0) {
            end_page++;
        }

        Skill skill = skillsDAO.getByName(skill_name);
        ArrayList<WishList> wishList = wishListDAO.getOfMentee(mentee_id);


        request.setAttribute("wishList", wishList);
        request.setAttribute("search", search);
        request.setAttribute("order", order_raw);
        request.setAttribute("skill_name", skill_name);
        request.setAttribute("total_mentor", totalMentor);
        request.setAttribute("end_page", end_page);
        request.setAttribute("page", page);
        request.setAttribute("skill", skill);
        request.setAttribute("level", level);
        request.setAttribute("list_mentor", list_mentor);
        request.getRequestDispatcher("view/mentee/search_mentor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
