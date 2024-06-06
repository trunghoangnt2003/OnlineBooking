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
    public ArrayList<Review> getMenteeReviewByMentorId(String id) {
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
