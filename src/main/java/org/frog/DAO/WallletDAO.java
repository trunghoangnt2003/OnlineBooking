package org.frog.DAO;

import org.frog.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WallletDAO {
    public Wallet getInfoWalletById(String id) {
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

    public ArrayList<Transaction> getAllTransactionByWalletId(int id) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select A.name, T.description, T.amount, T.fee, TT.name as type_name, T.date, S.type, S.id as status_id\n" +
                    "                    from Account A join Wallet W on A.wallet_id = W.id\n" +
                    "                    join [Transaction] T on W.id = T.wallet_opposite\n" +
                    "                    join Type_Transaction TT on TT.id = T.type_id\n" +
                    "\t\t\t\t\tjoin Status S on S.id = T.status_id\n" +
                    "                    where T.wallet_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                TypeTransaction typeTransaction = new TypeTransaction();
                Account account = new Account();
                Status status = new Status();
                transaction.setDate(resultSet.getDate("date"));
                transaction.setAmount(resultSet.getFloat("amount"));
                transaction.setFee(resultSet.getFloat("fee"));
                transaction.setDescription(resultSet.getString("description"));
                typeTransaction.setName(resultSet.getString("type_name"));
                account.setName(resultSet.getString("name"));
                status.setType(resultSet.getString("type"));
                status.setId(resultSet.getInt("status_id"));

                transaction.setStatus(status);
                transaction.setTypeTransaction(typeTransaction);
                transaction.setAccount(account);
                transactions.add(transaction);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }
}