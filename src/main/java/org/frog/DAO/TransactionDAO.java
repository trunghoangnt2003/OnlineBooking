package org.frog.DAO;

import org.frog.model.Transaction;
import org.frog.model.TypeTransaction;
import org.frog.model.Wallet;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    public int insertPayment(Transaction transaction) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Transaction]\n" +
                    "           ([date]\n" +
                    "           ,[amount]\n" +
                    "           ,[type_id]\n" +
                    "           ,[wallet_id]\n" +
                    "           ,[wallet_opposite]\n" +
                    "           ,[fee])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, transaction.getDate());
            statement.setDouble(2, transaction.getAmount());
            statement.setInt(3, transaction.getTypeTransaction().getId());
            statement.setInt(4, transaction.getWallet().getId());
            statement.setInt(5, transaction.getWallet().getId());
            statement.setDouble(6, transaction.getFee());
            statement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
