package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MenteeDAO;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;
import org.frog.model.WishList;

import java.io.IOException;
import java.util.List;

@WebServlet("/mentee/profile")
public class MenteeController extends AuthenticationServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String menteeId = req.getParameter("menteeid");
        MenteeDAO menteeDAO = new MenteeDAO();
        Mentee mentee = menteeDAO.getMenteeById(menteeId);
        req.setAttribute("mentee", mentee);

        WishListDAO dao = new WishListDAO();
        String id = req.getParameter("idwish");
        int wish_list_id = 0;
        if(id != null){
            wish_list_id = Integer.parseInt(id);
        }
        dao.UnfollowById(wish_list_id);


        List<WishList> wishListsAccepet = dao.getWishListAcceptByMenteeId(account.getId());

        if (id != null){
            resp.sendRedirect("profile?menteeid=" + menteeId);
            return;
        }

        req.setAttribute("acc_id", account.getId()) ;
        req.setAttribute("url_id", menteeId);
        req.setAttribute("wishlistA", wishListsAccepet) ;
        req.getRequestDispatcher("../view/mentee/profile/profile.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}

