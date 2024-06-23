package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentee;
import org.frog.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO {

    public void insertReview(Review review) {
        try {
            String sql = "\n" +
                    "INSERT INTO [dbo].[Review]\n" +
                    "           ([comment]\n" +
                    "           ,[date]\n" +
                    "           ,[rate]\n" +
                    "           ,[mentor_id]\n" +
                    "           ,[mentee_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,GetDate()\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, review.getComment());
            preparedStatement.setInt(2, review.getRate());
            preparedStatement.setString(3, review.getMentor().getAccount().getId());
            preparedStatement.setString(4, review.getMentee().getAccount().getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Review> getMenteeReviewByMentorId(String id) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT TOP 3 Account.name, Review.comment, Review.date, Review.rate\n" +
                    "    FROM     Account join Mentee on Mentee.account_id = Account.id JOIN\n" +
                    "    Review ON Mentee.account_id = Review.mentee_id\n" +
                    "    join Mentor ON Review.mentor_id = Mentor.account_id\n" +
                    "    where Mentor.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                Mentee mentee = new Mentee();
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                mentee.setAccount(account);
                review.setMentee(mentee);
                review.setComment(resultSet.getString("comment"));
                review.setDate(resultSet.getDate("date"));
                review.setRate(resultSet.getInt("rate"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    public ArrayList<Review> getAllReview(String id) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Account.name, Review.comment, Review.date, Review.rate\n" +
                    "    FROM     Account join Mentee on Mentee.account_id = Account.id JOIN\n" +
                    "    Review ON Mentee.account_id = Review.mentee_id\n" +
                    "    join Mentor ON Review.mentor_id = Mentor.account_id\n" +
                    "    where Mentor.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                Mentee mentee = new Mentee();
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                mentee.setAccount(account);
                review.setMentee(mentee);
                review.setComment(resultSet.getString("comment"));
                review.setDate(resultSet.getDate("date"));
                review.setRate(resultSet.getInt("rate"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

}
