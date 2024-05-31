package org.frog.DAO;

import org.frog.model.*;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleDAO {
    public ArrayList<Slot> getSlots(){
        String sql="SELECT id,time_start,time_end FROM Slot";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Slot> slots = new ArrayList<>();
            while (resultSet.next()){
                Slot slot = new Slot();
                slot.setId(resultSet.getInt("id"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));
                slots.add(slot);
            }
            return slots;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<BookingSchedule> getSchedulesByIDnDay(String id, Date start , Date end){
        ArrayList<BookingSchedule> schedules = new ArrayList<>();
        String sql="SELECT s.id,s.date,slot_id,s.account_id,bs.booking_id,bs.schedule_id,isAtend,status_id \n" +
                "FROM Schedule s \n" +
                "LEFT JOIN Booking_Schedule bs ON s.id = bs.schedule_id\n" +
                "WHERE s.account_id=? AND s.date >= ? AND s.date <= ?\n" +
                "ORDER BY s.date ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setDate(2, start);
            preparedStatement.setDate(3, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BookingSchedule bs = new BookingSchedule();
                Schedule s = new Schedule();
                s.setId(resultSet.getInt("id"));
                s.setDate(resultSet.getDate("date"));

                Mentor m = new Mentor();
                Account acc = new Account();
                acc.setId(resultSet.getString("account_id"));
                m.setAccount(acc);
                s.setMentor(m);

                Slot sl = new Slot();
                sl.setId(resultSet.getInt("slot_id"));
                s.setSlot(sl);
                bs.setSchedule(s);

                Booking b = new Booking();
                b.setId(resultSet.getInt("booking_id"));
                bs.setBooking(b);

                bs.setAttend(resultSet.getBoolean("isAtend"));
                Status st = new Status();
                st.setId(resultSet.getInt("status_id"));
                bs.setStatus(st);

                schedules.add(bs);
            }
            return schedules;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void insertDayFreeByMentor(String id , Date date ,int slot_id){
        String sql="INSERT INTO [dbo].[Schedule]\n" +
                "           ([date]\n" +
                "           ,[slot_id]\n" +
                "           ,[account_id])\n" +
                "     VALUES\n" +
                "           (?\n" +
                "           ,?\n" +
                "           ,?)\n";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);
            preparedStatement.setString(3, id);
            preparedStatement.setInt(2, slot_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDayFreeByMentor(String id , Date date ,int slot_id){
        String sql="DELETE FROM [dbo].[Schedule]\n" +
                "      WHERE  account_id=? AND date = ? AND slot_id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, slot_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
