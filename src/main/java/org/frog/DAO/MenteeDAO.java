package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentee;
import org.frog.model.Wallet;

import java.sql.*;

public class MenteeDAO {
    public Mentee getMenteeById(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC.getConnection();
            String sql = "SELECT * FROM Mentee " +
                    "JOIN Account ON Account.id = Mentee.account_id " +
                    "WHERE Mentee.account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setDob(resultSet.getDate("dob"));
                account.setPhone(resultSet.getString("phone"));
                account.setGender(resultSet.getInt("gender"));
                account.setAddress(resultSet.getString("address"));
                account.setUserName(resultSet.getString("username"));
                account.setEmail(resultSet.getString("mail"));
                account.setAvatar(resultSet.getString("avatar"));

                Mentee mentee = new Mentee(account);
                return mentee;
            } else {
                System.out.println("No mentee found with account_id: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the stack trace
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();  // Log the stack trace for resource cleanup issues
            }
        }
        return null;
    }


    public Wallet getWalletById(int id) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT * FROM Wallet\n" +
                    "where Wallet.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getInt("id"));
                wallet.setBalance(resultSet.getInt("balance"));
                return wallet;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateMentee(Mentee mentee) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Account]\n" +
                    "   SET \n" +
                    "     [username] = ?\n" +
                    "      ,[name] = ?\n" +
                    "      ,[dob] = ?\n" +
                    "      ,[phone] = ?\n" +
                    "      ,[gender] = ?\n" +
                    "      ,[address] = ?\n" +
                    "      ,[avatar] = null\n" +
                    " WHERE Account.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            System.out.println("update mentee 1");
            preparedStatement.setString(1, mentee.getAccount().getUserName());
            preparedStatement.setString(2, mentee.getAccount().getName());
            preparedStatement.setDate(3, mentee.getAccount().getDob());
            preparedStatement.setString(4, mentee.getAccount().getPhone());
            preparedStatement.setInt(5, mentee.getAccount().getGender());
            preparedStatement.setString(6, mentee.getAccount().getAddress());
            preparedStatement.setString(7, mentee.getAccount().getId());
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();  // Log the stack trace
        }
    }


    public static void main(String[] args) {
        Account a = new Account();
        a.setName("Doan Manh Gioi");
     //   a.setGender("");
        Mentee m = new Mentee();

    }
}



