package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Role;
import org.frog.model.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
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
    public Account getWalletAccountById(String account_id){
        Account account = new Account();
        String sql = "SELECT w.id,balance,available,a.id AS account_id,name,role_id FROM Wallet w \n" +
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
                wallet.setId(resultSet.getInt("id"));
                wallet.setBalance(resultSet.getFloat("balance"));
                wallet.setAvailable(resultSet.getFloat("available"));
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
    public void createTransaction(Timestamp date , float amount,int walletFrom , int walletTo , float fee ){
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
            stm.setInt(3,walletFrom);
            stm.setInt(4,walletTo);
            stm.setFloat(5,fee);
            stm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean payment(float amount,int wallet_id){
        String sql ="UPDATE [Wallet]\n" +
                "   SET [balance] = ?   \n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setFloat(1,amount);
            stm.setInt(2,wallet_id);
            int i = stm.executeUpdate();
            if(i>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean moneyBack(float amount,int wallet_id){
        String sql ="UPDATE [Wallet]\n" +
                "   SET [available] = ?   \n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setFloat(1,amount);
            stm.setInt(2,wallet_id);
            int i = stm.executeUpdate();
            if(i>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
