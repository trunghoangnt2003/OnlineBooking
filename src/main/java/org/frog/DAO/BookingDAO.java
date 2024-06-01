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
                booking.setDate(resultSet.getDate("create_date"));

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

        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select id, create_date" +
                        " from Booking" +
                         " where mentor_id = ?  and from_date = ? and to_date = ? and level_skill_id = ?"; //and mentee_id = ?
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, b.getMentor().getAccount().getId());
//            preparedStatement.setString(2, b.getMentee().getAccount().getId());
            preparedStatement.setDate(2, b.getStartDate());
            preparedStatement.setDate(3, b.getEndDate());
            preparedStatement.setInt(4, b.getLevel_skills().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));

                booking.setStatus(b.getStatus());
                booking.setAmount(b.getAmount());
                booking.setDate(resultSet.getDate("create_date"));
                booking.setMentor(b.getMentor());
                booking.setMentee(b.getMentee());
                booking.setStartDate(b.getStartDate());
                booking.setEndDate(b.getEndDate());
                booking.setDescription(b.getDescription());
                booking.setLevel_skills(b.getLevel_skills());
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
