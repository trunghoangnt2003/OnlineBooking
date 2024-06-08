package org.frog.controller.mentee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.WishListDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.frog.model.WishList;
import org.frog.utility.StatusEnum;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/mentee/follow")
public class FollowMentorController extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        handlePost(req, resp, account);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response, Account account) throws IOException {
        String mentee_id = account.getId();

        BufferedReader reader = request.getReader();
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(jsonBuffer.toString(), JsonObject.class);

        String mentor_id = json.get("mentorId").getAsString();

        WishListDAO wishListDAO = new WishListDAO();
        WishList wishList = wishListDAO.CheckFollow(mentee_id, mentor_id);

        if (wishList != null) {
            if(wishList.getStatus().getId() == StatusEnum.CANCEL){
                wishListDAO.followAgain(account.getId(), mentor_id);
            } else if (wishList.getStatus().getId() == StatusEnum.ACCEPTED) {
                wishListDAO.Unfollow(account.getId(), mentor_id);
            }
        } else {
            wishListDAO.Follow(account.getId(), mentor_id);
        }


    }
}

