package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String sql = "select Account.[name],Booking.id,Booking.mentor_id ,Booking.amount, Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date \n" +
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
                account.setId(resultSet.getString("mentor_id"));
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

    public ArrayList<Booking> getHistoryDoneAndAccept(String mentee_id ){
        ArrayList<Booking> bookingList = new ArrayList<>();

        try{

            Connection connection = JDBC.getConnection();
            String sql = "SELECT \n" +
                    "    Booking.id, \n" +
                    "\tBooking.status_id,\n" +
                    "\ts.type,\n" +
                    "    Booking.amount, \n" +
                    "    Booking.create_date,\n" +
                    "    Booking.mentor_id, \n" +
                    "    Booking.mentee_id, \n" +
                    "    Booking.from_date, \n" +
                    "    Booking.to_date,\n" +
                    "    Booking.level_skill_id, \n" +
                    "    Level_Skill.skill_id, \n" +
                    "    Skill.name AS skill_name, \n" +
                    "    Skill.src_icon, \n" +
                    "    Level_Skill.level_id, \n" +
                    "    [Level].[type], \n" +
                    "    Account.name AS mentor_name, \n" +
                    "    Account.mail\n" +
                    "FROM  \n" +
                    "    Booking \n" +
                    "INNER JOIN\n" +
                    "    Mentor ON Booking.mentor_id = Mentor.account_id \n" +
                    "INNER JOIN\n" +
                    "    Account ON Mentor.account_id = Account.id\n" +
                    "JOIN \n" +
                    "    Level_Skill ON Booking.level_skill_id = Level_Skill.id\n" +
                    "JOIN \n" +
                    "    Skill ON Level_Skill.skill_id = Skill.id \n" +
                    "JOIN \n" +
                    "    [Level] ON Level_Skill.level_id = [Level].id  \n" +
                    "JOIN Status s ON Booking.status_id = s.id\n" +
                    "WHERE \n" +
                    "    (Booking.status_id = ? OR Booking.status_id = ?) \n" +
                    "    AND Booking.mentee_id = ?\n" +
                    "ORDER BY \n" +
                    "    create_date DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.DONE);
            preparedStatement.setInt(2, StatusEnum.ACCEPTED);
            preparedStatement.setString(3, mentee_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("type"));
                booking.setStatus(status);

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

    public List<Booking> getAllRequestCancelOfBooking( String id) {
        List<Booking> bookingList = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name],Booking.mentor_id ,Booking.amount,Booking.id, Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date \n" +
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
                    "                    where Booking.mentee_id = ? and( Booking.status_id = 12 or Booking.status_id = 2) "+
                    "                    Order By create_date desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                account.setId(resultSet.getString("mentor_id"));
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

    public void cancelBooking(String id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Booking]\n" +
                    "   SET [status_id] = ?\n" +
                    "      ,[create_date] = GETDATE()\n" +
                    " WHERE Booking.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,StatusEnum.CANCEL);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Map<String,Integer> statisticByMentee(String id){
        Map<String,Integer> statistic = new HashMap<>();
        try{
            String sql = "Select b.status_id,s.type,Count(b.status_id) as sum_per_req\n" +
                    "From Booking b JOIN [Status] s on b.status_id = s.id\n" +
                    "Where mentee_id = ?\n" +
                    "Group By b.status_id, s.type";

            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statistic.put(resultSet.getString("type"),resultSet.getInt("sum_per_req"));
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return statistic;
    }

    public Map<String,Integer> getTotalBookByMentee(String id){
        Map<String,Integer> statistic = new HashMap<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select count(Booking_Schedule.id) as number_slot\n" +
                    "From Booking JOIN Booking_Schedule ON Booking.id = Booking_Schedule.booking_id\n" +
                    "WHERE mentee_id = ?\n" +
                    "And Booking_Schedule.status_id = ?\n" +
                    "or Booking_Schedule.status_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, StatusEnum.DONE);
            preparedStatement.setInt(3, StatusEnum.ACCEPTED);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                statistic.put("number_slot",resultSet.getInt("number_slot"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statistic;
    }

    public int totalRequestBook(String id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select count(id) as total\n" +
                    "From Booking "+
                    "WHERE mentee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Booking getById(int id) {
        try{
            Connection connection = JDBC.getConnection();
            String sql = "Select * from Booking where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               Booking booking = new Booking();
               booking.setId(resultSet.getInt("id"));
               booking.setAmount(resultSet.getInt("amount"));
               booking.setDate(resultSet.getTimestamp("create_date"));
               booking.setStartDate(resultSet.getDate("from_date"));
               booking.setEndDate(resultSet.getDate("to_date"));
               booking.setDescription(resultSet.getString("description"));


               Account accMentee = new Account();
                accMentee.setId(resultSet.getString("mentee_id"));
                Mentee mentee = new Mentee(accMentee);
               booking.setMentee(mentee);

               Account accMenter = new Account();
               accMenter.setId(resultSet.getString("mentor_id"));
               Mentor mentor = new Mentor(accMenter);
               booking.setMentor(mentor);

               Level_Skills level_skills = new Level_Skills();
               level_skills.setId(resultSet.getInt("level_skill_id"));
               booking.setLevel_skills(level_skills);

               Status status = new Status();
               status.setId(resultSet.getInt("status_id"));
               booking.setStatus(status);

               return booking;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
