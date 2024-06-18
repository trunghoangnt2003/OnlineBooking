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
                    "                    where Booking.mentee_id = ? and Booking.status_id = 12";
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
    public ArrayList<Booking> getMyBookingByMentee(String id ){
        ArrayList<Booking> bookings = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "  Select  b.id,b.status_id,status.type,\n" +
                    "\t\t\t\t\tb.amount,b.create_date,b.description,b.from_date,b.to_date,\n" +
                    "                    level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type as typeS,b.mentee_id,\n" +
                    "\t\t\t\t\tacc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone \n" +
                    "FROM Booking b\n" +
                    " INNER Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    "                    INNER Join [Level] l On ls.level_id = l.id\n" +
                    "                    INNER Join Skill sk On ls.skill_id = sk.id\n" +
                    "\t\t\t\t\tINNER JOIN Mentee m ON b.mentee_id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Status status ON status.id = b.status_id\n" +
                    "WHERE b.mentor_id = ? AND b.status_id = 11";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("type"));


                Level_Skills level_skill = new Level_Skills();
                level_skill.setId(resultSet.getInt("level_skill_id"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("skill_name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));
                level_skill.setSkill(skill);

                org.frog.model.Level level = new org.frog.model.Level();
                level.setId(resultSet.getInt("level_id"));
                level.setName(resultSet.getString("typeS"));
                level_skill.setLevel(level);

                Mentee mentee = new Mentee();
                Account account_mentee = new Account();
                account_mentee.setId(resultSet.getString("mentee_id"));
                account_mentee.setEmail(resultSet.getString("mail"));
                account_mentee.setName(resultSet.getString("name"));
                account_mentee.setGender(resultSet.getInt("gender"));
                account_mentee.setPhone(resultSet.getString("phone"));
                account_mentee.setAddress(resultSet.getString("address"));
                mentee.setAccount(account_mentee);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setMentee(mentee);
                booking.setLevel_skills(level_skill);
                booking.setStatus(status);
                bookings.add(booking);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookings;
    }
}
