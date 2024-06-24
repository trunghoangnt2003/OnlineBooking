package org.frog.DAO;

import org.frog.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    public int insertDeposit(Account account,float amount) {
        int kq = 0;
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
                    "           (GETDATE()\n" +
                    "           ,?\n" +
                    "           ,1\n" +
                    "           ,?\n" +
                    "           ,NULL\n" +
                    "           ,NULL)";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setFloat(1,amount);
            preparedStatement.setString(2,account.getWallet().getId());

            kq = preparedStatement.executeUpdate();
            WalletDAO walletDAO = new WalletDAO();
            walletDAO.updateDeposit(amount,account.getWallet().getId());
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return kq;
    }
}
