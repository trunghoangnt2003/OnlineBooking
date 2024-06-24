package org.frog.DAO;

import org.frog.model.*;

import java.sql.*;
import java.util.ArrayList;

public class WalletDAO {
    public Wallet getByAccountId(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC.getConnection();
            String sql = "SELECT w.[id]\n" +
                    "      ,[balance]\n" +
                    "      ,[hold]\n" +
                    "\t  \n" +
                    "  FROM [Prog_DB].[dbo].[Wallet] w join Account on Account.wallet_id = w.id\n" +
                    "  where Account.id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getString("id"));
                wallet.setBalance(resultSet.getFloat("balance"));
                wallet.setHold(resultSet.getFloat("hold"));
                return wallet;
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
    public void updateDeposit(float amount,String id){
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

            preparedStatement.setString(2, id);
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

    public void updateAvailable(Wallet wallet, float hold){
        try{

            String sql = "UPDATE [dbo].[Wallet]\n" +
                    "   SET [hold] = ?\n" +
                    " WHERE id = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, hold);
            preparedStatement.setString(2, wallet.getId());

            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Account getWalletAccountById(String account_id){
        Account account = new Account();
        String sql = "SELECT w.id,balance,hold,a.id AS account_id,name,role_id FROM Wallet w \n" +
                "JOIN Account a ON w.id = a.wallet_id\n" +
                "where a.id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,account_id);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                account.setId(resultSet.getString("account_id"));
                account.setName(resultSet.getString("name"));

                Wallet wallet = new Wallet();
                wallet.setId(resultSet.getString("id"));
                wallet.setBalance(resultSet.getFloat("balance"));
                wallet.setHold(resultSet.getFloat("hold"));
                account.setWallet(wallet);

                Role role = new Role();
                role.setId(resultSet.getInt("role_id"));
                account.setRole(role);
                return account;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }
    public void createTransaction(Timestamp date , float amount,String walletFrom , String walletTo , float fee ){
        String sql = "INSERT INTO [dbo].[Transaction]\n" +
                "           ([date]\n" +
                "           ,[amount]\n" +
                "           ,[type_id]\n" +
                "           ,[wallet_id]\n" +
                "           ,[wallet_opposite]\n" +
                "           ,[fee])          \n" +
                "     VALUES\n" +
                "           (?\n" +
                "           ,?\n" +
                "           ,3\n" +
                "           ,?\n" +
                "           ,?\n" +
                "           ,?)";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setTimestamp(1,date);
            stm.setFloat(2,amount);
            stm.setString(3,walletFrom);
            stm.setString(4,walletTo);
            stm.setFloat(5,fee);
            stm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean payment(float amount,String wallet_id){
        String sql ="UPDATE [Wallet]\n" +
                "   SET [balance] = ?   \n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setFloat(1,amount);
            stm.setString(2,wallet_id);
            int i = stm.executeUpdate();
            if(i>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean moneyBack(float amount,String wallet_id){
        String sql ="UPDATE [Wallet]\n" +
                "   SET [hold] = ?   \n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setFloat(1,amount);
            stm.setString(2,wallet_id);
            int i = stm.executeUpdate();
            if(i>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Transaction> getAllTransactionByWalletId(String id) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select A.name, T.id, T.amount, T.fee, TT.name as type_name, T.date\n" +
                    "                    from Account A join Wallet W on A.wallet_id = W.id\n" +
                    "                    join [Transaction] T on W.id = T.wallet_opposite\n" +
                    "                    join Type_Transaction TT on TT.id = T.type_id\n" +
                    //"\t\t\t\t\tjoin Status S on S.id = T.status_id\n" +
                    "                    where T.wallet_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                TypeTransaction typeTransaction = new TypeTransaction();
                Account account = new Account();
                transaction.setId(resultSet.getInt("id"));
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
