package org.frog.DAO;
import org.frog.model.Account;

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
                account.setUserName(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
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
            PreparedStatement preparedStatement = connection.prepareCall(sql    );
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String passWord = resultSet.getString("password");
                String userName = resultSet.getNString("username");
                user = new Account();
                user.setUserName(userName);
                user.setPassword(passWord);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return user;
    }
    public int insertUser(Account account) {
        int kq = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareCall(sql);

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

    public int updatePassWord(String email, String newPass) {
        int result = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "update [account]"
                    + "set password=?"
                    + "where email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, email);
            result = preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return result;
    }
}
