package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Transaction;
import org.frog.model.TypeTransaction;
import org.frog.model.Wallet;

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
            String sql = "select A.name, T.amount, T.fee, TT.name as type_name, T.date\n" +
                    "\t\tfrom Account A join Wallet W on A.wallet_id = W.id\n" +
                    "\t\tjoin [Transaction] T on W.id = T.wallet_opposite\n" +
                    "\t\tjoin Type_Transaction TT on TT.id = T.type_id\n" +
                    "\t\twhere T.wallet_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                TypeTransaction typeTransaction = new TypeTransaction();
                Account account = new Account();
                transaction.setDate(resultSet.getDate("date"));
                transaction.setAmount(resultSet.getFloat("amount"));
                transaction.setFee(resultSet.getFloat("fee"));
                typeTransaction.setName(resultSet.getString("type_name"));
                account.setName(resultSet.getString("name"));

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