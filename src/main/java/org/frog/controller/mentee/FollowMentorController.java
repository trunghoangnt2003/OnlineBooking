package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;

@WebServlet("/mentee/follow")
public class FollowMentorController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        WishListDAO dao = new WishListDAO();
        boolean b = dao.CheckFollow(account.getId(), "c7129ebe-ca47-42d7-8ad9-fd9c0006859f");
        if(b == true){
            dao.followAgain(account.getId(), "c7129ebe-ca47-42d7-8ad9-fd9c0006859f");
        }else{
            dao.Follow(account.getId(), "c7129ebe-ca47-42d7-8ad9-fd9c0006859f");
        }
       resp.sendRedirect("profile");
    }
}

