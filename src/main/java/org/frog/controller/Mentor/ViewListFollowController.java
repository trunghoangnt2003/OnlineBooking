package org.frog.controller.Mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.WishList;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/view_follower")
public class ViewListFollowController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String action = req.getParameter("action");
        String requestId = req.getParameter("requestId");

        resp.sendRedirect("/Frog/mentor/view_follower");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String id = account.getId();
        WishListDAO dao = new WishListDAO();
        ArrayList<WishList> wishLists = dao.getWishListByMentorId(id);

        req.setAttribute("id", id);
        req.setAttribute("wishLists", wishLists);
        req.getRequestDispatcher("../view/mentor/view_follower.jsp").forward(req, resp);
    }
}
