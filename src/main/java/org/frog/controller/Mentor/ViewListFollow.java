package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;
import org.frog.model.WishList;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/follower")
public class ViewListFollow extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        WishListDAO dao = new WishListDAO();
        ArrayList<WishList> wishLists = dao.getWishListByMentorId(account.getId());

        req.setAttribute("wishLists", wishLists);
        req.getRequestDispatcher("../view/mentor/follower.jsp").forward(req, resp);
    }
}
