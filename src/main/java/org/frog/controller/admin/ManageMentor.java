package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.LogDAO;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentor;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/mentor")
public class ManageMentor extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String action = request.getParameter("action");
        // Xử lý request dựa trên action
        switch (action) {
            case "ban":
                // Thực hiện ban user
                banUser(userId, account.getId());
                response.setStatus(HttpServletResponse.SC_OK);
                break;
            case "unban":
            case "active":
                // Thực hiện reinstate user
                reinstateUser(userId, account.getId());
                response.setStatus(HttpServletResponse.SC_OK);
                break;
            default:
                // Xử lý lỗi nếu action không hợp lệ
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }
    private void banUser(String userId,String idAdmin) {
        // Thực hiện logic để ban user
        LogDAO logDAO = new LogDAO();
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.updateStatusById(userId,6);
        logDAO.updateStatusByAdmin(userId,idAdmin,6);
        System.out.println("Banning user with ID: " + userId);

    }

    private void reinstateUser(String userId,String idAdmin) {
        // Thực hiện logic để reinstate user
        LogDAO logDAO = new LogDAO();
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.updateStatusById(userId,7);
        logDAO.updateStatusByAdmin(userId,idAdmin,7);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        String name = request.getParameter("name");
        if(name == null || name.isEmpty()) name = "";
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        MentorDAO mentorDAO = new MentorDAO();
        ArrayList<Mentor> list_mentor = mentorDAO.getMentorAndPagingAndSearch(page,name);
        int totalMentor = mentorDAO.totalMentorAndPagingAndSearch(page,name);
        int end_page = totalMentor /4 ;
        if ( totalMentor % 4 != 0) {
            end_page++;
        }
        request.setAttribute("page",page);
        request.setAttribute("totalMentor",totalMentor);
        request.setAttribute("name",name);
        request.setAttribute("end_page",end_page);
        request.setAttribute("list",list_mentor);
        request.getRequestDispatcher("../view/admin/viewAllMentor.jsp").forward(request,resp);
    }
}
