package org.frog.controller.mentee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.ReviewDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;
import org.frog.model.Mentor;
import org.frog.model.Review;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/mentee/review")
public class ReviewController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        handlePost(request, response, account);
    }


    private void handlePost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {

        String mentee_id = account.getId();

        BufferedReader reader = request.getReader();
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(jsonBuffer.toString(), JsonObject.class);

        String mentor_id = json.get("mentor_id").getAsString();
        String comment = json.get("comment").getAsString();
        String rating_raw = json.get("rating").getAsString();
        int rating = 0;
        try{
            rating = Integer.parseInt(rating_raw);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        MentorDAO mentorDAO = new MentorDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        Review review = new Review();

        Account acc_mentee = new Account();
        acc_mentee.setId(mentee_id);
        Mentee mentee = new Mentee();
        mentee.setAccount(acc_mentee);
        review.setMentee(mentee);

        Account acc_mentor = new Account();
        acc_mentor.setId(mentor_id);
        Mentor mentor = new Mentor();
        mentor.setAccount(acc_mentor);
        review.setMentor(mentor);

        review.setComment(comment);
        review.setRate(rating);

        reviewDAO.insertReview(review);
        Mentor mentor2 = mentorDAO.getMentorById(mentor_id);
        float mentor2_rating = mentor2.getRating();
        mentor2.setRating((mentor2_rating + rating) / 2);
        mentorDAO.updateRatingById(mentor_id,mentor2.getRating());


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", "success");
        response.getWriter().write(gson.toJson(jsonResponse));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {

    }
}
