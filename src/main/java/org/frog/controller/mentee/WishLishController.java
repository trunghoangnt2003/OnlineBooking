package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.WishList;

import java.io.IOException;
import java.util.List;

@WebServlet("/mentee/wishlist")
public class WishLishController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        WishListDAO dao = new WishListDAO();
        List<WishList> wishLists = dao.getWishListProcessByMenteeId(account.getId());
        req.setAttribute("wishLists", wishLists);
        req.getRequestDispatcher("../view/mentee/wish_list.jsp").forward(req, resp);
    }
}
