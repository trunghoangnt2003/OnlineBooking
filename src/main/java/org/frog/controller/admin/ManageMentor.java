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

@WebServlet("/admin/mentor")
public class ManageMentor extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        String name = request.getParameter("name");
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Mentor> list_mentor = mentorDAO.getMentorAndPagingAndSearch(page,name);
        int totalMentor = mentorDAO.totalMentor();
        int end_page = totalMentor / 5;
        if ( totalMentor % 5 != 0) {
            end_page++;
        }
        System.out.println(end_page);
        request.setAttribute("page",page);
        request.setAttribute("end_page",end_page);
        request.setAttribute("list",list_mentor);
        request.getRequestDispatcher("../view/admin/viewAllMentor.jsp").forward(request,resp);
    }
}
