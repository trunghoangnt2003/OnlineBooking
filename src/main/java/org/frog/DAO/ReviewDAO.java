package org.frog.DAO;

import org.frog.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
                    "           ,<?)";
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

}
