package org.frog.DAO;

import org.frog.model.*;

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
            String sql = "SELECT Account.name, Wish_List.status, Wish_List.date\n" +
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
                account.setName(resultSet.getString("name"));

                mentee.setAccount(account);
                wishList.setMentee(mentee);
                Status status = new Status();
                status.setId(resultSet.getInt("status"));
                wishList.setStatus(status);
                wishList.setTimeRequest(resultSet.getDate("date"));
                wishLists.add(wishList);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return wishLists;
    }
}
