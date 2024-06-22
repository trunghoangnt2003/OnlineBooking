package org.frog.DAO;

import org.frog.model.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDAO {

    public Wallet getByAccountId(String id) {
        Wallet wallet = new Wallet();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Wallet.id, Wallet.balance, Wallet.available\n" +
                    "FROM     Account INNER JOIN\n" +
                    "                  Wallet ON Account.wallet_id = Wallet.id\n" +
                    "\t\t\t\t  where Account.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                wallet.setId(resultSet.getInt("id"));
                wallet.setBalance(resultSet.getFloat("balance"));
                wallet.setAvailable(resultSet.getFloat("available"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wallet;
    }

    public void updateAvailable(Wallet wallet, float available){
        try{

            String sql = "UPDATE [dbo].[Wallet]\n" +
                    "   SET [available] = ?\n" +
                    " WHERE id = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, available);
            preparedStatement.setInt(2, wallet.getId());

            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
