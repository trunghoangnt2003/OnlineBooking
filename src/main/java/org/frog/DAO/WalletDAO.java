package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentee;
import org.frog.model.Wallet;

import java.sql.*;

public class WalletDAO {
    public Wallet getByAccountId(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC.getConnection();
            String sql = "SELECT w.[id]\n" +
                    "      ,[balance]\n" +
                    "      ,[available]\n" +
                    "\t  \n" +
                    "  FROM [Prog_DB].[dbo].[Wallet] w join Account on Account.wallet_id = w.id\n" +
                    "  where Account.id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getInt(1));
                wallet.setBalance(resultSet.getFloat(2));
                wallet.setAvailable(resultSet.getFloat(3));
                return wallet;
            } else {

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
    public void updateDeposit(float amount,int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Wallet]\n" +
                    "   SET [balance] = [balance] + ?\n" +
                    " WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1,amount);

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

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

    }

    public Account getWalletAccountById(String id) {
        return null;
    }

    public void payment(float v, int id) {
    }

    public void createTransaction(Timestamp timestamp, int amount, int id, int id1, float fee) {
    }
}
