package org.frog.DAO.Mentor;

import org.frog.DAO.JDBC;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.model.Schedule;
import org.frog.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MentorDAO {
    public ArrayList<Schedule> getScheduleByMentorID(String id) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT \n" +
                    "\tsche.id\n" +
                    "      [start_date]\n" +
                    "      ,[end_date]\n" +
                    "      ,[status],s.type\n" +
                    "      ,[account_id]\n" +
                    "      ,[booking_id]\n" +
                    "  FROM [Schedule] sche INNER JOIN Status s ON  sche.status = s.id\n" +
                    "  Where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDateStart(resultSet.getDate("start_date"));
                schedule.setDateEnd(resultSet.getDate("end_date"));
                Status s = new Status();
                s.setId(resultSet.getInt("status"));
                s.setType(resultSet.getString("type"));
                schedule.setStatus(s);
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                schedule.setBooking(booking);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
        }
        return schedules;
    }
}
