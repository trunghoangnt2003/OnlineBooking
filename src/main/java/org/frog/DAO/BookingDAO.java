package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

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

    public List<Booking> getAllRequestOfBooking( String id) {
        List<Booking> bookingList = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name], Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date  \n" +
                    "                    from Booking join [dbo].[Level_Skill]\n" +
                    "                    on Booking.level_skill_id = [dbo].[Level_Skill].id\n" +
                    "                    join [dbo].[Level]\n" +
                    "                    on [dbo].[Level_Skill].level_id=[dbo].[Level].id\n" +
                    "                    join Skill \n" +
                    "                    on Skill.id = [dbo].[Level_Skill].skill_id\n" +
                    "                    join Mentor\n" +
                    "                    on Booking.mentor_id = Mentor.account_id\n" +
                    "                    join Account\n" +
                    "                    on Account.id = Mentor.account_id\n" +
                    "\t\t\t\t\twhere Booking.mentee_id = ?";
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

    public void insertBooking(Booking booking) {

        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Booking]\n" +
                    "           ([status_id]\n" +
                    "           ,[amount]\n" +
                    "           ,[create_date]\n" +
                    "           ,[mentor_id]\n" +
                    "           ,[mentee_id]\n" +
                    "           ,[from_date]\n" +
                    "           ,[to_date]\n" +
                    "           ,[description]\n" +
                    "           ,[level_skill_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,GETDATE()\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, booking.getStatus().getId());
            preparedStatement.setInt(2, booking.getAmount());
            preparedStatement.setString(3, booking.getMentor().getAccount().getId());
            preparedStatement.setString(4, booking.getMentee().getAccount().getId());
            preparedStatement.setDate(5, booking.getStartDate());
            preparedStatement.setDate(6, booking.getEndDate());
            preparedStatement.setString(7, booking.getDescription());
            preparedStatement.setInt(8, booking.getLevel_skills().getId());
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Booking findByInfo(Booking b) {
        Booking booking = null;

        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT TOP 1 id, create_date " +
                    "FROM Booking " +
                    "WHERE mentor_id = ? AND mentee_id = ? AND from_date = ? AND to_date = ? AND level_skill_id = ? " +
                    "ORDER BY create_date DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, b.getMentor().getAccount().getId());
            preparedStatement.setString(2, b.getMentee().getAccount().getId());
            preparedStatement.setDate(3, b.getStartDate());
            preparedStatement.setDate(4, b.getEndDate());
            preparedStatement.setInt(5, b.getLevel_skills().getId());

            System.out.println("SQL: " + preparedStatement.toString());
            System.out.println("Mentor ID: " + b.getMentor().getAccount().getId());
            System.out.println("Mentee ID: " + b.getMentee().getAccount().getId());
            System.out.println("Start Date: " + b.getStartDate());
            System.out.println("End Date: " + b.getEndDate());
            System.out.println("Level Skills ID: " + b.getLevel_skills().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setStatus(b.getStatus());
                booking.setAmount(b.getAmount());
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setMentor(b.getMentor());
                booking.setMentee(b.getMentee());
                booking.setStartDate(b.getStartDate());
                booking.setEndDate(b.getEndDate());
                booking.setDescription(b.getDescription());
                booking.setLevel_skills(b.getLevel_skills());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    public ArrayList<Booking> getHistoryDone(String mentee_id ){
        ArrayList<Booking> bookingList = new ArrayList<>();

        try{

            Connection connection = JDBC.getConnection();
            String sql = "SELECT Booking.id,  Booking.amount, Booking.create_date,\n" +
                    "\t\tBooking.mentor_id, Booking.mentee_id, Booking.from_date, Booking.to_date,\n" +
                    "\t\tBooking.level_skill_id, Level_Skill.skill_id, Skill.name as skill_name, Skill.src_icon , Level_Skill.level_id, [Level].[type] , Account.name as mentor_name, Account.mail\n" +
                    "FROM  Booking INNER JOIN\n" +
                    "      Mentor ON Booking.mentor_id = Mentor.account_id INNER JOIN\n" +
                    "      Account ON Mentor.account_id = Account.id\n" +
                    "\t  JOIN Level_Skill ON Booking.level_skill_id = Level_Skill.id\n" +
                    "\t  LEFT JOIN Skill ON Level_Skill.skill_id = Skill.id \n" +
                    "\t  LEFT JOIN [Level] ON Level_Skill.level_id =  [Level].id  \n" +
                    "WHERE Booking.status_id = ? AND Booking.mentee_id = ? \n" +
                    "Order By create_date desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.DONE);
            preparedStatement.setString(2, mentee_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));

                Account acc_mentor = new Account();
                acc_mentor.setId(resultSet.getString("mentor_id"));
                acc_mentor.setName(resultSet.getString("mentor_name"));
                acc_mentor.setEmail(resultSet.getString("mail"));
                Mentor mentor = new Mentor();
                mentor.setAccount(acc_mentor);
                booking.setMentor(mentor);


                Account acc_mentee = new Account();
                acc_mentee.setId(resultSet.getString("mentee_id"));
                Mentee mentee = new Mentee();
                mentee.setAccount(acc_mentee);
                booking.setMentee(mentee);

                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));

                Level_Skills level_skill = new Level_Skills();
                level_skill.setId(resultSet.getInt("level_skill_id"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("skill_name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));
                level_skill.setSkill(skill);

                Level level = new Level();
                level.setId(resultSet.getInt("level_id"));
                level.setName(resultSet.getString("type"));
                level_skill.setLevel(level);

                booking.setLevel_skills(level_skill);

                bookingList.add(booking);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return bookingList;
    }

}
