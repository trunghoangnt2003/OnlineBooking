package org.frog.DAO;

import org.frog.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Booking_ScheduleDAO {
    public ArrayList<BookingSchedule> getBookingScheduleByMentor(String mentor_id){
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "  Select s.id as schedule_id, bs.id,bs.status_id,status.type,s.account_id as mentor_id,s.date,s.slot_id, bs.booking_id,\n" +
                    "\t\t\t\t\tb.amount,b.create_date,b.description,b.from_date,b.to_date,\n" +
                    "                    level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type,b.mentee_id,\n" +
                    "\t\t\t\t\tacc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "                    From Schedule s \n" +
                    "\t\t\t\t\tINNER Join Booking_Schedule bs  on  s.id = bs.schedule_id\n" +
                    "                    INNER Join Booking b ON bs.booking_id = b.id \n" +
                    "                    INNER Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    "                    INNER Join [Level] l On ls.level_id = l.id\n" +
                    "                    INNER Join Skill sk On ls.skill_id = sk.id\n" +
                    "\t\t\t\t\tINNER JOIN Mentee m ON b.mentee_id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Status status ON status.id = bs.status_id\n" +
                    "                    Where s.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,mentor_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("type"));
                bookingSchedule.setStatus(status);

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));

                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                schedule.setMentor(mentor);

                schedule.setDate(resultSet.getDate("date"));

                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                schedule.setSlot(slot);

                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                booking.setAmount(resultSet.getFloat("amount"));
                booking.setDate(resultSet.getDate("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));


                Level_Skills level_skill = new Level_Skills();
                level_skill.setId(resultSet.getInt("level_skill_id"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("skill_name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));
                level_skill.setSkill(skill);

                org.frog.model.Level level = new org.frog.model.Level();
                level.setId(resultSet.getInt("level_id"));
                level.setName(resultSet.getString("type"));
                level_skill.setLevel(level);
                booking.setLevel_skills(level_skill);

                Mentee mentee = new Mentee();
                Account account_mentee = new Account();
                account_mentee.setId(resultSet.getString("mentee_id"));
                account_mentee.setEmail(resultSet.getString("mail"));
                account_mentee.setName(resultSet.getString("name"));
                account_mentee.setGender(resultSet.getInt("gender"));
                account_mentee.setPhone(resultSet.getString("phone"));
                account_mentee.setAddress(resultSet.getString("address"));
                mentee.setAccount(account_mentee);
                booking.setMentee(mentee);
                bookingSchedule.setBooking(booking);
                list.add(bookingSchedule);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
    public ArrayList<Booking> getBookingByMenteeBookMentor(String id ){
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
                    "WHERE b.mentor_id = ? AND b.status_id = 1";
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
                booking.setAmount(resultSet.getFloat("amount"));
                booking.setDate(resultSet.getDate("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setMentee(mentee);
                booking.setLevel_skills(level_skill);
                booking.setStatus(status);
                bookings.add(booking);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public boolean updateScheduleBookings(int id,int status_id) throws SQLException {
        Connection connection = JDBC.getConnection();
        try {
            int numbersCheck = 0;
            connection.setAutoCommit(false);
            String sql_number_check = " SELECT COUNT(booking_id) AS numberSlots FROM Booking_Schedule where booking_id =?";
            PreparedStatement stm = connection.prepareStatement(sql_number_check);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                numbersCheck = rs.getInt("numberSlots");
            }

            String sql_update_sbs = "UPDATE [Booking_Schedule]\n" +
                    "   SET \n" +
                    "      [status_id] = ?\n" +
                    " WHERE booking_id = ?";
            PreparedStatement stm_update_sbs = connection.prepareStatement(sql_update_sbs);
            stm_update_sbs.setInt(1, status_id);
            stm_update_sbs.setInt(2, id);
            int numberUpdate = stm_update_sbs.executeUpdate();
            if(numbersCheck == numberUpdate){
                connection.commit();
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Booking_ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Booking_ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(Booking_ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    public boolean deleteScheduleBookings(int id) throws SQLException {
        Connection connection = JDBC.getConnection();
        try {
            int numbersCheck = 0;
            connection.setAutoCommit(false);
            String sql_number_check = " SELECT COUNT(booking_id) AS numberSlots FROM Booking_Schedule where booking_id =?";
            PreparedStatement stm = connection.prepareStatement(sql_number_check);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                numbersCheck = rs.getInt("numberSlots");
            }

            String sql_update_sbs = "DELETE FROM [Booking_Schedule]\n" +
                    "      WHERE booking_id = ?\n";
            PreparedStatement stm_update_sbs = connection.prepareStatement(sql_update_sbs);
            stm_update_sbs.setInt(1, id);
            int numberUpdate = stm_update_sbs.executeUpdate();
            if(numbersCheck == numberUpdate){
                connection.commit();
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Booking_ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Booking_ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(Booking_ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    public  void updateBooking(int id , int status_id ) throws SQLException {
        String sql = "UPDATE [Booking]\n" +
                "   SET \n" +
                "      [status_id] = ?\n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,status_id);
            stm.setInt(2,id);
            stm.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
