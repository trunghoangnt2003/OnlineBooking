package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.model.Mentor;
import org.frog.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BookingDAO {
    public List<Booking> getAllRequestOfBooking() {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name], Booking.from_date, Booking.to_date, Booking.[description], Skill.[name],Level.type \n" +
                    "from Booking join [dbo].[Level_Skill]\n" +
                    "on Booking.level_skill_id = [dbo].[Level_Skill].id\n" +
                    "join [dbo].[Level]\n" +
                    "on [dbo].[Level_Skill].level_id=[dbo].[Level].id\n" +
                    "join Skill \n" +
                    "on Skill.id = [dbo].[Level_Skill].skill_id\n" +
                    "join Mentor\n" +
                    "on Booking.mentor_id = Mentor.account_id\n" +
                    "join Account\n" +
                    "on Account.id = Mentor.account_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                Mentor mentor = new Mentor(account);

                Skill skill = new Skill();
                skill.setName(resultSet.getString("name"));





            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
