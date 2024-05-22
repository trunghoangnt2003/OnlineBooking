package org.frog.DAO.Mentor;

import org.frog.DAO.JDBC;
import org.frog.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MentorDAO {
    public ArrayList<Schedule> getScheduleByMentorID(String id) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC.getConnection();
            String sql = "SELECT \n" +
                    "\tsche.id,\n" +
                    "      [start_date]\n" +
                    "      ,[end_date]\n" +
                    "      ,[status]\n" +
                    "      ,[account_id]\n" +
                    "  FROM [Schedule] sche\n" +
                    "  Where account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDateStart(resultSet.getTimestamp("start_date"));
                schedule.setDateEnd(resultSet.getTimestamp("end_date"));
                schedule.setStatus(resultSet.getBoolean("status"));

                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return schedules;
    }

    public void updateFreeTimeOfMentor(java.sql.Timestamp start, java.sql.Timestamp end, String id) {
        String sql = "INSERT INTO [Schedule] ([start_date], [end_date], [status], [account_id]) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            preparedStatement.setBoolean(3, true); // Assuming status is a boolean
            preparedStatement.setString(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Booking> getBookingbyMentorID(String id) {
        ArrayList<Booking> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC.getConnection();
            String sql = "SELECT b.id,status_id,s.type,amount,date,mentee_id,a.name,a.phone,a.mail,a.dob,a.gender,a.address,start_date,end_date FROM Booking b INNER JOIN Status s \n" +
                    "ON s.id = b.status_id INNER JOIN Mentee m ON m.account_id = b.mentee_id \n" +
                    "INNER JOIN Account a ON a.id = m.account_id\n" +
                    "WHERE mentor_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking book = new Booking();
                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("type"));
                book.setStatus(status);
                book.setAmount(resultSet.getFloat("amount"));
                book.setDate(resultSet.getDate("date"));
                Mentee mentee = new Mentee();
               Account acc = new Account();
               acc.setId(resultSet.getString("mentee_id"));
               acc.setName(resultSet.getString("name"));
               acc.setEmail(resultSet.getString("mail"));
               acc.setDob(resultSet.getDate("dob"));
               acc.setAddress(resultSet.getString("address"));
               acc.setPhone(resultSet.getString("phone"));
               mentee.setAccount(acc);
               book.setMentee(mentee);
                book.setStartDate(resultSet.getTimestamp("start_date"));
                book.setEndDate(resultSet.getTimestamp("end_date"));
                bookings.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bookings;
    }
}