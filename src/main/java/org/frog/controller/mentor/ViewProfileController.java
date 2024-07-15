package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.ReviewDAO;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

@WebServlet("/mentor/profile")
public class ViewProfileController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            String mentorId = req.getParameter("mentorid");
            MentorDAO mentorDAO = new MentorDAO();
            Mentor mentor = mentorDAO.getMentorById(mentorId);
            Mentor mentorAuthor = mentorDAO.getMentorById(account.getId());
            Level_SkillDAO level_skillDAO = new Level_SkillDAO();
            ArrayList<Level_Skills> level_skills = level_skillDAO.getLevel_SkillByMentorId(mentorId);
            ReviewDAO reviewDAO = new ReviewDAO();
            ArrayList<Review> reviews = reviewDAO.getMenteeReviewByMentorId(mentorId);
            ArrayList<Review> allReviews = reviewDAO.getAllReview(mentorId);
            WishListDAO dao = new WishListDAO();
            //đếm booking
            ArrayList<WishList> wishLists = dao.getWishListByMentorId(mentorId);
            // list wishlist
            ArrayList<WishList> wish = dao.getByMentorId(mentorId);
            boolean isAuthor = mentorId.equals(account.getId());

            Currency vnd = Currency.getInstance("VND");
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat vndFormatter = NumberFormat.getCurrencyInstance(localeVN);

            vndFormatter.setCurrency(vnd);
            req.setAttribute("price", vndFormatter.format(mentor.getPrice()));

            if (isAuthor) {
                if(mentorAuthor.getEducation() == null &&
                        mentorAuthor.getExperience() == null &&
                        mentorAuthor.getProfileDetail() == null &&
                        mentorAuthor.getPrice() == 0) {
                    resp.sendRedirect("/Frog/mentor/create_profile");
                }
            }

            if(!resp.isCommitted()) {
                req.setAttribute("numberReview", allReviews.size());
                req.setAttribute("account", account);
                req.setAttribute("list_follow", wish);
                req.setAttribute("isAuthor", isAuthor);
                req.setAttribute("numberFollower", mentor.getTotalBookings());
                req.setAttribute("level_skills", level_skills);
                req.setAttribute("mentor", mentor);
                req.setAttribute("review", reviews);
                req.setAttribute("id", mentorId);
                req.getRequestDispatcher("../view/mentor/view_profile.jsp").forward(req, resp);
            }

        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
