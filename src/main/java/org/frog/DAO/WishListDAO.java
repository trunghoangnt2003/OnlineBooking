package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Level_Skills;
import org.frog.model.Mentee;
import org.frog.model.WishList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishListDAO {
    public ArrayList<WishList> getWishListByMentorId(String id) {
        ArrayList<WishList> wishLists = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Account.id, Account.name, Wish_List.status_id, Wish_List.date\n" +
                    "FROM    Wish_List join Mentee on Wish_List.mentee_id = Mentee.account_id\n" +
                    "\t\t\tjoin Account on Mentee.account_id = Account.id\n" +
                    "\t\t\t\t  where Wish_List.mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WishList wishList = new WishList();
                Mentee mentee = new Mentee();
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));

                mentee.setAccount(account);
                wishList.setMentee(mentee);
                wishList.setStatus(resultSet.getInt("status_id"));
                wishList.setTimeRequest(resultSet.getDate("date"));
                wishLists.add(wishList);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return wishLists;
    }

    public void deleteFollower(String id) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "DELETE FROM Wish_List WHERE mentee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateStatusAccept(String id, int status) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE Wish_List SET status_id = ? WHERE mentee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatusReject(String id, int status) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE Wish_List SET status_id = ? WHERE mentee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
