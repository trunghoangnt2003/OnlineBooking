package org.frog.DAO;

import org.frog.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public int CalcBookByMentor(String mentorId) {
        try {

            Connection connection = JDBC.getConnection();
            String sql = "Select Count(mentee_id) as number_booking\n" +
                    "From Booking \n" +
                    "Where mentor_id = ? and status_id = 3";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number_booking");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Booking> getAllRequestProcessOfBooking( String id) {
        List<Booking> bookingList = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name],Booking.id,Booking.amount, Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date \n" +
                    "                                        from Booking join [dbo].[Level_Skill]\n" +
                    "                                        on Booking.level_skill_id = [dbo].[Level_Skill].id\n" +
                    "                                        join [dbo].[Level]\n" +
                    "                                        on [dbo].[Level_Skill].level_id=[dbo].[Level].id\n" +
                    "                                        join Skill \n" +
                    "                                        on Skill.id = [dbo].[Level_Skill].skill_id\n" +
                    "                                        join Mentor\n" +
                    "                                       on Booking.mentor_id = Mentor.account_id\n" +
                    "                                        join Account\n" +
                    "                                        on Account.id = Mentor.account_id\n" +
                    "                    where Booking.mentee_id = ? and Booking.status_id = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                Mentor mentor = new Mentor(account);

                Skill skill = new Skill();
                skill.setName(resultSet.getString("skill"));
                Level level = new Level();
                level.setName(resultSet.getString("type"));
                Level_Skills levelSkills = new Level_Skills();
                levelSkills.setSkill(skill);
                levelSkills.setLevel(level);

                Booking booking = new Booking();
                booking.setMentor(mentor);
                booking.setLevel_skills(levelSkills);
                booking.setAmount(resultSet.getInt("amount"));
                booking.setId(resultSet.getInt("id"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setDate(resultSet.getTimestamp("create_date"));

                bookingList.add(booking);

            }
            return bookingList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Booking> getAllRequestCancelOfBooking( String id) {
        List<Booking> bookingList = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name],Booking.amount,Booking.id, Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date \n" +
                    "                                        from Booking join [dbo].[Level_Skill]\n" +
                    "                                        on Booking.level_skill_id = [dbo].[Level_Skill].id\n" +
                    "                                        join [dbo].[Level]\n" +
                    "                                        on [dbo].[Level_Skill].level_id=[dbo].[Level].id\n" +
                    "                                        join Skill \n" +
                    "                                        on Skill.id = [dbo].[Level_Skill].skill_id\n" +
                    "                                        join Mentor\n" +
                    "                                       on Booking.mentor_id = Mentor.account_id\n" +
                    "                                        join Account\n" +
                    "                                        on Account.id = Mentor.account_id\n" +
                    "                    where Booking.mentee_id = ? and Booking.status_id = 12";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                Mentor mentor = new Mentor(account);

                Skill skill = new Skill();
                skill.setName(resultSet.getString("skill"));
                Level level = new Level();
                level.setName(resultSet.getString("type"));
                Level_Skills levelSkills = new Level_Skills();
                levelSkills.setSkill(skill);
                levelSkills.setLevel(level);

                Booking booking = new Booking();
                booking.setMentor(mentor);
                booking.setLevel_skills(levelSkills);
                booking.setId(resultSet.getInt("id"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setAmount(resultSet.getInt("amount"));
                bookingList.add(booking);

            }
            return bookingList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBooking(String id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Booking]\n" +
                    "   SET [status_id] = 12\n" +
                    "      ,[create_date] = GETDATE()\n" +
                    " WHERE Booking.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
