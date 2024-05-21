package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Status;
import org.frog.utility.StatusEnum;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountDAO {
    public ArrayList<Account> selectAll() {
        ArrayList<Account> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [Account]";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String passWord = resultSet.getString("password");
                String userName = resultSet.getNString("username");
                String email = resultSet.getNString("mail");
                int status = resultSet.getInt("status");
                Account user = new Account();
                user.setUserName(userName);
                 user.setPassword(passWord);
                 user.setEmail(email);
                 user.setStatus(new Status(status,""));
                 list.add(user);

            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
        }
        return list;
    }

    public Account getLogin(String userName, String passWord) {
        Account account = null;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [account]"
                    + "where [username]=? AND [password]=?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getString("id"));
                account.setUserName(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                account.setStatus(new Status(resultSet.getInt("status"),""));
            }
            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return account;
    }

    public Account getLoginGoogle(String email) {
        Account user = null;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [Account]"
                    + "where [mail]=?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String passWord = resultSet.getString("password");
                String userName = resultSet.getNString("username");
                user = new Account();
                user.setUserName(userName);
                user.setPassword(passWord);
                user.setEmail(email);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return user;
    }
    // add thuoc tinh can dang ki
    public int register() {
        int kq = 0;
        return kq;
    }
    public void register(String id, String email, String password, int role) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Account]\n" +
                    "           ([id]\n" +
                    "           ,[mail]\n" +
                    "           ,[username]\n" +
                    "           ,[password]\n" +
                    "           ,[status]\n" +
                    "           ,[role_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,password);
            preparedStatement.setInt(5, StatusEnum.INACTIVE);
            preparedStatement.setInt(6,role);

            preparedStatement.executeUpdate();

            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
    }
    public int insertUser(Account account) {
        int kq = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Account]\n" +
                    "           ([id]\n" +
                    "           ,[name]\n" +
                    "           ,[dob]\n" +
                    "           ,[phone]\n" +
                    "           ,[gender]\n" +
                    "           ,[address]\n" +
                    "           ,[mail]\n" +
                    "           ,[username]\n" +
                    "           ,[password]\n" +
                    "           ,[wallet_id]\n" +
                    "           ,[status]\n" +
                    "           ,[role_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1,account.getId());
            preparedStatement.setString(2,account.getName());
            preparedStatement.setDate(3,account.getDob());
            preparedStatement.setString(4,account.getPhone());
            preparedStatement.setBoolean(5,account.getGender());
            preparedStatement.setString(6,account.getAddress());
            preparedStatement.setString(7,account.getEmail());
            preparedStatement.setString(8,account.getUserName());
            preparedStatement.setString(9,account.getPassword());
            preparedStatement.setInt(10,account.getWallet().getId());
            preparedStatement.setInt(11,account.getStatus().getId());
            preparedStatement.setInt(12,account.getRole().getId());
            kq = preparedStatement.executeUpdate();

            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return kq;
    }

    public int deleteUser(String id) {
        int result = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "delete from [account] where id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            result = preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return result;
    }

    public void updatePassWord(String email, String newPass) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "update [account]\n" +
                    "set [password]=?\n" +
                    "where [mail]=?";
            PreparedStatement preparedStatement = null;
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
                System.out.println("pass 1");
                preparedStatement.setString(1, newPass);
                preparedStatement.setString(2, email);
                System.out.println("pass 2");
                preparedStatement.executeUpdate();
                System.out.println("pass 3");
                JDBC.closeConnection(connection);
            }

        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
    }
    public void updateStatus(String email,int status) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "update [account]\n"
                    + "set status= ?\n"
                    + "where mail= ?\n";
            PreparedStatement preparedStatement = null;
            if (connection != null) {
                System.out.println("update status 1 ");
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, status);
                preparedStatement.setString(2, email);
                System.out.println("update status 2");
                preparedStatement.executeUpdate();
                System.out.println("update status 3");
            }

            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
    }
}
