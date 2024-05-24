package org.frog.DAO.Mentor;

import org.frog.DAO.JDBC;
import org.frog.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

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

    public ArrayList<BookingSchedule> getBookingbyMentorID(String id) {
        ArrayList<BookingSchedule> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC.getConnection();
            String sql = "SELECT b.id,b.mentee_id,create_date,amount,from_date,to_date,b.description AS des,level_id,ls.description, bs.schedule_id,bs.start_date,bs.end_date,\n" +
                    "b.status_id,s.type,acc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "FROM Booking b INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id  \n" +
                    "INNER JOIN Level_Skill ls ON ls.id = b.level_skill_id\n" +
                    "INNER JOIN Status s ON s.id = b.status_id\n" +
                    "INNER JOIN Mentee m ON m.account_id = b.mentee_id\n" +
                    "INNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "WHERE b.mentor_id =?\n" +
                    "ORDER BY start_date";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account acc = new Account();
                acc.setId(resultSet.getString("mentee_id"));
                acc.setName(resultSet.getString("name"));
                acc.setAddress(resultSet.getString("address"));
                acc.setDob(resultSet.getDate("dob"));
                acc.setGender(resultSet.getInt("gender"));
                acc.setEmail(resultSet.getString("mail"));
                acc.setPhone(resultSet.getString("phone"));

                Status s = new Status();
                s.setId(resultSet.getInt("status_id"));
                s.setType(resultSet.getString("type"));

                // luu level skill trong skill
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("level_id"));
                skill.setName(resultSet.getString("description"));

                Schedule sche = new Schedule();
                sche.setId(resultSet.getInt("schedule_id"));
                sche.setDateStart(resultSet.getTimestamp("start_date"));
                sche.setDateEnd(resultSet.getTimestamp("end_date"));

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setAmount(resultSet.getFloat("amount"));
                // get created date
                booking.setDate(resultSet.getDate("create_date"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setDes(resultSet.getString("des"));

                BookingSchedule bs = new BookingSchedule();
                bs.setAccount(acc);
                bs.setSkill(skill);
                bs.setStatus(s);
                bs.setBooking(booking);
                bs.setSchedule(sche);
                bookings.add(bs);
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

    public BookingSchedule getBookNScheID(Timestamp start, Timestamp end, String id,String menteeID) {
        BookingSchedule bs = new BookingSchedule();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC.getConnection();
            String sql = "SELECT s.id,bs.booking_id,s.status,b.status_id FROM Schedule s \n" +
                    "INNER JOIN Booking_Schedule bs ON  bs.schedule_id = s.id\n" +
                    "INNER JOIN Booking b ON b.id = bs.booking_id\n" +
                    "WHERE bs.start_date = ? AND bs.end_date = ? \n" +
                    "AND account_id=? \n" +
                    "AND b.mentee_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, start);
            preparedStatement.setTimestamp(2, end);
            preparedStatement.setString(3, id);
            preparedStatement.setString(4, menteeID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking b = new Booking();
                b.setId(resultSet.getInt("booking_id"));
                Schedule s = new Schedule();
                s.setId(resultSet.getInt("id"));
                s.setStatus(resultSet.getBoolean("status"));
                bs.setBooking(b);
                bs.setSchedule(s);
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

        return bs;
    }
    public  void updateBusyMentorSchedule(int sche_id) {
        String sql = "UPDATE Schedule SET status = ? WHERE id = ?";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, sche_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void updateResultsForMenteeRequest(int booking_id,int opt){
        String sql = "UPDATE Booking SET status_id = ? WHERE id = ?";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, opt);
            preparedStatement.setInt(2, booking_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Schedule> getBookedScheduleMentee(String id , int bookingID) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT s.id,bs.booking_id,s.status,b.status_id,bs.start_date,bs.end_date FROM Schedule s \n" +
                "INNER JOIN Booking_Schedule bs ON  bs.schedule_id = s.id\n" +
                "INNER JOIN Booking b ON b.id = bs.booking_id\n" +
                "WHERE account_id= ? AND s.id = ? AND b.status_id = ?";
        try (Connection con = JDBC.getConnection();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, id);
            stm.setInt(2, bookingID);
            stm.setInt(3, 3);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(rs.getInt("id"));
                schedule.setDateStart(rs.getTimestamp("start_date"));
                schedule.setDateEnd(rs.getTimestamp("end_date"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }
    public ArrayList<BookingSchedule> getInfoByDayID(String id, Timestamp start,Timestamp end ) {
        ArrayList<BookingSchedule> bookings = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC.getConnection();
            String sql = "SELECT b.id,b.mentee_id,create_date,amount,from_date,to_date,b.description AS des,level_id,ls.description, bs.schedule_id,bs.start_date,bs.end_date,\n" +
                    "b.status_id,s.type,acc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "FROM Booking b INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id  \n" +
                    "INNER JOIN Level_Skill ls ON ls.id = b.level_skill_id\n" +
                    "INNER JOIN Status s ON s.id = b.status_id\n" +
                    "INNER JOIN Mentee m ON m.account_id = b.mentee_id\n" +
                    "INNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "WHERE b.mentor_id = ? \n" +
                    "and bs.start_date >= ? AND bs.start_date <= ?\n" +
                    "AND status_id = ?\n" +
                    "ORDER BY start_date";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, end);
            preparedStatement.setInt(4, 3);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account acc = new Account();
                acc.setId(resultSet.getString("mentee_id"));
                acc.setName(resultSet.getString("name"));
                acc.setAddress(resultSet.getString("address"));
                acc.setDob(resultSet.getDate("dob"));
                acc.setGender(resultSet.getInt("gender"));
                acc.setEmail(resultSet.getString("mail"));
                acc.setPhone(resultSet.getString("phone"));

                Status s = new Status();
                s.setId(resultSet.getInt("status_id"));
                s.setType(resultSet.getString("type"));

                // luu level skill trong skill
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("level_id"));
                skill.setName(resultSet.getString("description"));

                Schedule sche = new Schedule();
                sche.setId(resultSet.getInt("schedule_id"));
                sche.setDateStart(resultSet.getTimestamp("start_date"));
                sche.setDateEnd(resultSet.getTimestamp("end_date"));

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setAmount(resultSet.getFloat("amount"));
                // get created date
                booking.setDate(resultSet.getDate("create_date"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setDes(resultSet.getString("des"));

                BookingSchedule bs = new BookingSchedule();
                bs.setAccount(acc);
                bs.setSkill(skill);
                bs.setStatus(s);
                bs.setBooking(booking);
                bs.setSchedule(sche);
                bookings.add(bs);
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