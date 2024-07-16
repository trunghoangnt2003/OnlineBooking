package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentor;
import org.frog.model.Mentor_Schedule;
import java.sql.*;
public class Mentor_ScheduleDAO {
    public Mentor_Schedule getByMentor(String mentorId) {
        try{
            String sql = "SELECT [id]\n" +
                    "      ,[mentor_id]\n" +
                    "      ,[start_date]\n" +
                    "      ,[end_date]\n" +
                    "  FROM [dbo].[Mentor_Schedule]\n" +
                    "  Where mentor_id = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("id"));
                Account account = new Account();
                account.setId(resultSet.getString("mentor_id"));
                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor_schedule.setMentor(mentor);

                mentor_schedule.setStart_date(resultSet.getDate("start_date"));
                mentor_schedule.setEnd_date(resultSet.getDate("end_date"));
                return mentor_schedule;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void updateEndDate(int id, java.util.Date end) {
        try{
            String sql = "UPDATE [dbo].[Mentor_Schedule]\n" +
                    "   SET [end_date] = ?\n" +
                    " WHERE id = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(end.getTime()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStartDate(int id, java.util.Date start) {
        try{
            String sql = "UPDATE [dbo].[Mentor_Schedule]\n" +
                    "   SET [start_date] = ?\n" +
                    " WHERE id = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(start.getTime()));
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMessage(int id, String message) {
        try{
            String sql = "UPDATE [dbo].[Mentor_Schedule]\n" +
                    "   SET [message] = ?\n" +
                    " WHERE id = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Mentor_Schedule getById(int id) {
        try{
            String sql = "SELECT [id]\n" +
                    "      ,[mentor_id]\n" +
                    "      ,[start_date]\n" +
                    "      ,[end_date]\n" +
                    "  FROM [dbo].[Mentor_Schedule]\n" +
                    "  Where id = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("id"));
                Account account = new Account();
                account.setId(resultSet.getString("mentor_id"));
                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor_schedule.setMentor(mentor);

                mentor_schedule.setStart_date(resultSet.getDate("start_date"));
                mentor_schedule.setEnd_date(resultSet.getDate("end_date"));
                return mentor_schedule;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

}
