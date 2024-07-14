package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentor_CV_Log;
import org.frog.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mentor_CV_LogDAO {
    public void updateStatus(String mentor_id, int status_id) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Mentor_CV_Logs]\n" +
                    "   SET [status_id] = ?\n" +
                    " WHERE Mentor_CV_Logs.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status_id);
            preparedStatement.setString(2, mentor_id);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Status getStatusCVLog(String id) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT status_id FROM [dbo].[Mentor_CV_Logs]\n" +
                    " WHERE Mentor_CV_Logs.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                return status;
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Mentor_CV_Log getMentorCVLogByAccountId(String accountId) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT * FROM [dbo].[Mentor_CV_Logs]\n" +
                    " WHERE Mentor_CV_Logs.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Mentor_CV_Log mentorCVLog = new Mentor_CV_Log();
                Account account = new Account();
                account.setId(accountId);
                mentorCVLog.setAccount(account);
                mentorCVLog.setProfileDetail(resultSet.getString("profile_detail"));
                mentorCVLog.setExperience(resultSet.getString("experience"));
                mentorCVLog.setEducation(resultSet.getString("education"));
                mentorCVLog.setPrice(resultSet.getInt("price"));
                return mentorCVLog;
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean isHaveAccount(String id) {
        String result = null;

        try {
            Connection connection = JDBC.getConnection();
            String sql = "select M.account_id from Mentor_CV_Logs M " +
                    "where M.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("account_id");
                return true;
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public Mentor_CV_Log getCVLog(String id) {
        Mentor_CV_Log mentorCvLog = new Mentor_CV_Log();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT * FROM [dbo].[Mentor_CV_Logs]\n" +
                    " WHERE Mentor_CV_Logs.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                Status status = new Status();
                account.setId(id);
                mentorCvLog.setAccount(account);
                mentorCvLog.setProfileDetail(resultSet.getString("profile_detail"));
                mentorCvLog.setExperience(resultSet.getString("experience"));
                mentorCvLog.setEducation(resultSet.getString("education"));
                mentorCvLog.setPrice(resultSet.getInt("price"));
                status.setId(resultSet.getInt("status_id"));
                mentorCvLog.setStatus(status);
                return mentorCvLog;
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
