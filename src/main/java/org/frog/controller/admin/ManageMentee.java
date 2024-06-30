package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MenteeDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;

import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/mentee")
public class ManageMentee extends AuthenticationServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String name = req.getParameter("searchname");
        MenteeDAO menteeDAO = new MenteeDAO();
        if(name == null){
            name = "";
        }
        Map<Mentee,Map<String,Integer>> menteeMapMap= menteeDAO.getStaticAllMentee(name);

        req.setAttribute("menteeMap", menteeMapMap);
        req.setAttribute("size",menteeMapMap.size());
        req.getRequestDispatcher("../view/admin/manageMentee.jsp").forward(req, resp);
    }
}
