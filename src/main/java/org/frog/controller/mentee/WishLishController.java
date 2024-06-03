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
        String id = req.getParameter("idwish");
        int wish_list_id = 0;
        if(id != null){
            wish_list_id = Integer.parseInt(id);
        }
        dao.Unfollow(wish_list_id);
        List<WishList> wishLists = dao.getWishListProcessByMenteeId(account.getId());
        List<WishList> wishListsAccepet = dao.getWishListAcceptByMenteeId(account.getId());
        req.setAttribute("wishLists", wishLists);
        req.setAttribute("wishlistA", wishListsAccepet) ;
        req.getRequestDispatcher("../view/mentee/wish_list.jsp").forward(req, resp);
    }
}
