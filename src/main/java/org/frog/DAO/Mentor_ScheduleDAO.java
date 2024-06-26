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
}
