package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Booking_ScheduleDAO {

    public ArrayList<BookingSchedule> getBookingScheduleByMentor(String mentor_id){
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "Select s.id as schedule_id, bs.id,bs.status_id,s.mentor_schedule_id,ms.mentor_id as mentor_id,s.date,s.slot_id, bs.booking_id,\n" +
                    " b.level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type,\n" +
                    " b.id as booking_id, b.mentee_id as mentee_id\n" +
                    " From Schedule s JOIN Mentor_Schedule ms on s.mentor_schedule_id = ms.id\n" +
                    " Left Join Booking_Schedule bs  on  s.id = bs.schedule_id\n" +
                    " Left Join Booking b ON bs.booking_id = b.id\n" +
                    " Left Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    " Left Join [Level] l On ls.level_id = l.id\n" +
                    " Left Join Skill sk On ls.skill_id = sk.id\n" +
                    " Where ms.mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,mentor_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                bookingSchedule.setStatus(status);

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));

                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("mentor_schedule_id"));

                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                mentor.setAccount(account_mentor);
                mentor_schedule.setMentor(mentor);
                schedule.setMentorSchedule(mentor_schedule);

                schedule.setDate(resultSet.getDate("date"));

                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                schedule.setSlot(slot);

                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                bookingSchedule.setBooking(booking);

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
                mentee.setAccount(account_mentee);
                booking.setMentee(mentee);
                list.add(bookingSchedule);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void makeBooking_Schedule(Booking booking, ArrayList<Integer> scheduleList){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Booking_Schedule]\n" +
                    "           ([booking_id]\n" +
                    "           ,[schedule_id]\n" +
                    "           ,[status_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (Integer schedule : scheduleList) {
                System.out.println("schedule : " + schedule);
                preparedStatement.setInt(1, booking.getId());
                preparedStatement.setInt(2, schedule);
                preparedStatement.setInt(3, StatusEnum.PROCESSING);
                preparedStatement.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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
    public  void rejectBooking(int id , int status_id,String des ) throws SQLException {
        String sql = "UPDATE [Booking]\n" +
                "   SET \n" +
                "      [status_id] = ?\n" +
                "      ,[reason] = ?\n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,status_id);
            stm.setString(2,des);
            stm.setInt(3,id);
            stm.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateBookingDetail(int id , boolean isAttend ) throws SQLException {
        String sql = "UPDATE [Booking_Schedule]\n" +
                "   SET \n" +
                "      [isAtend] = ?\n" +
                "      ,[status_id] = ?\n" +
                " WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setBoolean(1,isAttend);
            stm.setInt(2,3);
            stm.setInt(3,id);
            stm.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<BookingSchedule> getDetailBooking(String mentor_id , Date date , int slot_id) throws SQLException {
        ArrayList<BookingSchedule> bs = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = " Select s.id as schedule_id, bs.id,bs.status_id,b.status_id AS status,bs.isAtend,status.type as status_booking_detail,s.mentor_schedule_id,ms.mentor_id as mentor_id,s.date,s.slot_id,sl.time_start,sl.time_end ,bs.booking_id,\n" +
                    "                     b.amount,b.create_date,b.description,b.from_date,b.to_date,\n" +
                    "                    level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type,b.mentee_id,\n" +
                    "                    acc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "                    From Schedule s \n" +
                    "                    INNER JOIN Mentor_Schedule ms ON s.mentor_schedule_id = ms.id\n" +
                    "                   INNER Join Booking_Schedule bs  on  s.id = bs.schedule_id\n" +
                    "                    INNER Join Booking b ON bs.booking_id = b.id \n" +
                    "                    INNER Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    "                    INNER Join [Level] l On ls.level_id = l.id\n" +
                    "                   INNER Join Skill sk On ls.skill_id = sk.id\n" +
                    "                    INNER JOIN Mentee m ON b.mentee_id = m.account_id\n" +
                    "                    INNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "                    INNER JOIN Status status ON status.id = bs.status_id\n" +
                    "                   INNER JOIN Slot sl ON sl.id = s.slot_id\n" +
                    "                    Where ms.mentor_id = ? AND date = ? AND slot_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,mentor_id);
            preparedStatement.setDate(2,date);
            preparedStatement.setInt(3,slot_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));
                bookingSchedule.setAttend(resultSet.getBoolean("isAtend"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("status_booking_detail"));
                bookingSchedule.setStatus(status);

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));

                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("mentor_schedule_id"));

                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                mentor.setAccount(account_mentor);
                mentor_schedule.setMentor(mentor);
                schedule.setMentorSchedule(mentor_schedule);

                schedule.setDate(resultSet.getDate("date"));
                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));
                schedule.setSlot(slot);

                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                Status status_booking = new Status();
                status_booking.setId(resultSet.getInt("status"));
                booking.setStatus(status_booking);

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


                bs.add(bookingSchedule);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bs;
    }

    public ArrayList<BookingSchedule> getDetailBookings(String mentor_id ) throws SQLException {
        ArrayList<BookingSchedule> bs = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = " Select s.id as schedule_id, bs.id,bs.status_id,b.status_id AS status,bs.isAtend,status.type as status_booking_detail,s.mentor_schedule_id,ms.mentor_id as mentor_id,s.date,s.slot_id,sl.time_start,sl.time_end ,bs.booking_id,\n" +
                    "\t\t\t\t\tb.amount,b.create_date,b.description,b.from_date,b.to_date,\n" +
                    "                    level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type,b.mentee_id,\n" +
                    "\t\t\t\t\tacc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "                    From Schedule s \n" +
                    "INNER JOIN Mentor_Schedule ms ON s.mentor_schedule_id = ms.id"+
                    "\t\t\t\t\tINNER Join Booking_Schedule bs  on  s.id = bs.schedule_id\n" +
                    "                    INNER Join Booking b ON bs.booking_id = b.id \n" +
                    "                    INNER Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    "                    INNER Join [Level] l On ls.level_id = l.id\n" +
                    "                    INNER Join Skill sk On ls.skill_id = sk.id\n" +
                    "\t\t\t\t\tINNER JOIN Mentee m ON b.mentee_id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Status status ON status.id = bs.status_id\n" +
                    "\t\t\t\t\tINNER JOIN Slot sl ON sl.id = s.slot_id\n" +
                    "                    Where ms.mentor_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,mentor_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));
                bookingSchedule.setAttend(resultSet.getBoolean("isAtend"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("status_booking_detail"));
                bookingSchedule.setStatus(status);

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));

                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("mentor_schedule_id"));
                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                mentor.setAccount(account_mentor);
                mentor_schedule.setMentor(mentor);
                schedule.setMentorSchedule(mentor_schedule);


                schedule.setDate(resultSet.getDate("date"));

                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));
                schedule.setSlot(slot);

                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                Status status_booking = new Status();
                status_booking.setId(resultSet.getInt("status"));
                booking.setStatus(status_booking);

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

                bs.add(bookingSchedule);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bs;
    }
    public ArrayList<Booking> getBookingsHistory(String id ){
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
                    "WHERE b.mentor_id = ? AND b.status_id = 2 OR b.status_id = 13 ";
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public int getNumberOfInvited(String id){
        int number = 0;
        try{
            String sql="Select COUNT(id) AS numberOfInvited\n" +
                    "FROM Booking b\n" +
                    "WHERE b.mentor_id = ?";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                number = resultSet.getInt("numberOfInvited");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }
    public int getNumberOfAccepted(String id){
        int number = 0;
        try{
            String sql="Select COUNT(id) AS numberOfAccepted\n" +
                    "FROM Booking b\n" +
                    "Where (b.status_id = 11 OR b.status_id = 3 ) AND b.mentor_id = ?";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                number = resultSet.getInt("numberOfAccepted");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }
    public int getNumberOfRejected(String id){
        int number = 0;
        try{
            String sql="Select COUNT(id) AS numberOfRejected\n" +
                    "FROM Booking b\n" +
                    "Where b.status_id = 2 AND b.mentor_id = ?";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                number = resultSet.getInt("numberOfRejected");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }
    public int getNumberOfWaiting(String id){
        int number = 0;
        try{
            String sql="Select COUNT(id) AS numberOfWaiting\n" +
                    "FROM Booking b\n" +
                    "Where b.status_id = 1 AND b.mentor_id = ?";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                number = resultSet.getInt("numberOfWaiting");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }
    public float getRating(String id){
        float number = 0;
        try{
            String sql="SELECT rating FROM Mentor\n" +
                    "Where  account_id = ?";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                number = resultSet.getFloat("rating");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }
    public ArrayList<Booking> getBookingsHistoryByCreateDay(String id ){
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
                    "WHERE b.mentor_id = ? ORDER BY create_date DESC ";
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public ArrayList<Booking> getBookingsHistoryByDay(String id, Date SelectDay ){
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
                    "WHERE b.mentor_id = ? AND create_date = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setDate(2,SelectDay);
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public ArrayList<Booking> getBookingsHistoryByMoney(String id ){
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
                    "WHERE b.mentor_id = ? ORDER BY amount DESC ";
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public ArrayList<Booking> getBookingsHistoryByStatus(String id,int status_id ){
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
                    "WHERE b.mentor_id = ? AND status_id =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setInt(2,status_id);
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }

    public ArrayList<BookingSchedule> getLogs(int booking_id){
        ArrayList<BookingSchedule> list = new ArrayList<>();
        String sql = "SELECT sbl.id, sbl.booking_id, b.mentee_id, b.level_skill_id, sbl.schedule_id, \n" +
                "s.mentor_schedule_id,ms.mentor_id, s.date, s.slot_id, sl.time_start, sl.time_end \n" +
                "FROM Booking b \n" +
                "INNER JOIN Schedule_Booking_Logs sbl ON b.id = sbl.booking_id \n" +
                "INNER JOIN Schedule s ON s.id = sbl.schedule_id \n" +
                "INNER JOIN Mentor_Schedule ms ON s.mentor_schedule_id = ms.id\n" +
                "INNER JOIN Slot sl ON sl.id = s.slot_id " +
                "WHERE b.id = ? " +
                "ORDER BY s.date DESC";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, booking_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BookingSchedule bookingSchedule = new BookingSchedule();
                    bookingSchedule.setId(resultSet.getInt("id"));

                    Schedule schedule = new Schedule();
                    schedule.setId(resultSet.getInt("schedule_id"));
                    schedule.setDate(resultSet.getDate("date"));

                    Slot slot = new Slot();
                    slot.setId(resultSet.getInt("slot_id"));
                    slot.setStart_at(resultSet.getString("time_start"));
                    slot.setEnd_at(resultSet.getString("time_end"));
                    schedule.setSlot(slot);


                    Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                    mentor_schedule.setId(resultSet.getInt("mentor_schedule_id"));
                    Mentor mentor = new Mentor();
                    Account account_mentor = new Account();
                    account_mentor.setId(resultSet.getString("mentor_id"));
                    mentor.setAccount(account_mentor);
                    mentor_schedule.setMentor(mentor);
                    schedule.setMentorSchedule(mentor_schedule);

                    bookingSchedule.setSchedule(schedule);

                    Booking booking = new Booking();
                    booking.setId(resultSet.getInt("booking_id"));

                    Level_Skills level_skills = new Level_Skills();
                    level_skills.setId(resultSet.getInt("level_skill_id"));
                    booking.setLevel_skills(level_skills);

                    Mentee mentee = new Mentee();
                    Account account_mentee = new Account();
                    account_mentee.setId(resultSet.getString("mentee_id"));
                    mentee.setAccount(account_mentee);
                    booking.setMentee(mentee);
                    bookingSchedule.setBooking(booking);
                    list.add(bookingSchedule);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<BookingSchedule> getBookingScheduleMentorAccepted(String mentor_id){
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "Select bs.id, bs.booking_id,bs.schedule_id,s.mentor_schedule_id,mentor_id,s.date, s.slot_id\n" +
                    "from Booking_Schedule bs join Schedule s ON bs.schedule_id = s.id\n" +
                    "JOIN Mentor_Schedule ms ON s.mentor_schedule_id = ms.id\n" +
                    "Where (status_id = ? or status_id = ?) and ms.mentor_id = ? \n"+
                    "Order by [date] Desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,StatusEnum.ACCEPTED);
            preparedStatement.setInt(2,StatusEnum.DONE);
            preparedStatement.setString(3,mentor_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                bookingSchedule.setBooking(booking);

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));


                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("mentor_schedule_id"));
                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                mentor.setAccount(account_mentor);
                mentor_schedule.setMentor(mentor);
                schedule.setMentorSchedule(mentor_schedule);

                schedule.setDate(resultSet.getDate("date"));
                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                schedule.setSlot(slot);


                bookingSchedule.setSchedule(schedule);
                list.add(bookingSchedule);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void saveLog(ArrayList<BookingSchedule> bookingScheduless ){
        try{
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Schedule_Booking_Logs]\n" +
                    "           ([booking_id]\n" +
                    "           ,[schedule_id]\n" +
                    "           ,[status_id])\n" +
                    "     VALUES\n" +
                    "           (? \n" +
                    "           ,? \n" +
                    "           ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(BookingSchedule bs : bookingScheduless){
                preparedStatement.setInt(1,bs.getBooking().getId());
                preparedStatement.setInt(2, bs.getSchedule().getId());
                preparedStatement.setInt(3, bs.getStatus().getId());

                preparedStatement.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<BookingSchedule> getAllByBookingId(int id){
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT [id]\n" +
                    "      ,[booking_id]\n" +
                    "      ,[schedule_id]\n" +
                    "      ,[isAtend]\n" +
                    "      ,[status_id]\n" +
                    "  FROM [dbo].[Booking_Schedule]\n" +
                    "  Where booking_id = ?\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));
                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                bookingSchedule.setBooking(booking);

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                bookingSchedule.setStatus(status);

                list.add(bookingSchedule);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<BookingSchedule> getDetailBookingMentee(int booking_id) throws SQLException {
        ArrayList<BookingSchedule> bs = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = " Select s.id as schedule_id, bs.id,bs.status_id,b.status_id AS status,bs.isAtend,status.type as status_booking_detail,s.mentor_schedule_id,ms.mentor_id,s.date,s.slot_id,sl.time_start,sl.time_end ,bs.booking_id,\n" +
                    "\t\t\t\t\tb.amount,b.create_date,b.description,b.from_date,b.to_date,\n" +
                    "                    level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type,b.mentee_id,\n" +
                    "\t\t\t\t\tacc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "                    From Schedule s \n" +
                    "INNER JOIN Mentor_Schedule ms ON s.mentor_schedule_id = ms.id \n"+
                    "\t\t\t\t\tINNER Join Booking_Schedule bs  on  s.id = bs.schedule_id\n" +
                    "                    INNER Join Booking b ON bs.booking_id = b.id \n" +
                    "                    INNER Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    "                    INNER Join [Level] l On ls.level_id = l.id\n" +
                    "                    INNER Join Skill sk On ls.skill_id = sk.id\n" +
                    "\t\t\t\t\tINNER JOIN Mentee m ON b.mentee_id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "\t\t\t\t\tINNER JOIN Status status ON status.id = bs.status_id\n" +
                    "\t\t\t\t\tINNER JOIN Slot sl ON sl.id = s.slot_id\n" +
                    "                    Where bs.booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,booking_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setId(resultSet.getInt("id"));
                bookingSchedule.setAttend(resultSet.getBoolean("isAtend"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("status_booking_detail"));
                bookingSchedule.setStatus(status);

                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("schedule_id"));

                Mentor_Schedule mentor_schedule = new Mentor_Schedule();
                mentor_schedule.setId(resultSet.getInt("mentor_schedule_id"));
                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                mentor.setAccount(account_mentor);
                mentor_schedule.setMentor(mentor);
                schedule.setMentorSchedule(mentor_schedule);

                schedule.setDate(resultSet.getDate("date"));

                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));
                schedule.setSlot(slot);

                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setId(resultSet.getInt("booking_id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                Status status_booking = new Status();
                status_booking.setId(resultSet.getInt("status"));
                booking.setStatus(status_booking);

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
                bs.add(bookingSchedule);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bs;
    }
    public int getNumberOfSlotConfirmed(int booking_id) throws SQLException {
        int number = 0;
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT COUNT(booking_id) AS number FROM Booking_Schedule\n" +
                    "WHERE status_id = 3 AND booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,booking_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                number = resultSet.getInt("number");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }

    public ArrayList<BookingSchedule> getBookingSchedulesById(int bookingId){
        ArrayList<BookingSchedule> bookingSchedules = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "SELECT id,booking_id,schedule_id,status_id FROM Booking_Schedule WHERE booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BookingSchedule bs = new BookingSchedule();
                Status s = new Status();
                s.setId(resultSet.getInt("status_id"));

                Booking b = new Booking();
                b.setId(resultSet.getInt("booking_id"));

                Schedule sche = new Schedule();
                sche.setId(resultSet.getInt("schedule_id"));
                bs.setBooking(b);
                bs.setSchedule(sche);
                bs.setStatus(s);
                bookingSchedules.add(bs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookingSchedules;
    }
    public ArrayList<Booking> getBookingIdDuplicateSlotByBookingID(int bookingId){
        ArrayList<Booking> bookings = new ArrayList<>();
        try{
            String sql ="WITH T1 AS(\n" +
                    "SELECT b.id,schedule_id FROM Booking b\n" +
                    "INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id\n" +
                    "WHERE booking_id = ?\n" +
                    "),\n" +
                    "T2 AS (\n" +
                    "SELECT b.id,bs.schedule_id FROM Booking b\n" +
                    "INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id\n" +
                    "WHERE b.status_id=1\n" +
                    ")\n" +
                    "SELECT DISTINCT T2.id AS BookingDuplicateId\n" +
                    "FROM T1 \n" +
                    "INNER JOIN T2 ON T1.schedule_id = T2.schedule_id\n";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,bookingId);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("BookingDuplicateId"));
                bookings.add(booking);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookings;
    }
    public ArrayList<Booking> filterBookingsHistoryByStatus(String id, int statusId ){
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
                    "WHERE b.mentor_id = ? AND b.status_id = ?  ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setInt(2,statusId);
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public ArrayList<Booking> filterBookingsHistoryByStatusAndDate(String id, int statusId , Date start , Date end){
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
                    "WHERE b.mentor_id = ? AND b.status_id = ? AND from_date >= ? AND to_date <= ?  ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setInt(2,statusId);
            preparedStatement.setDate(3,start);
            preparedStatement.setDate(4,end);
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public ArrayList<Booking> filterBookingsHistoryByDate(String id, Date start , Date end){
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
                    "WHERE b.status_id = 2 OR b.status_id = 13 AND b.mentor_id = ?  AND from_date >= ? AND to_date <= ?  ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setDate(2,start);
            preparedStatement.setDate(3,end);
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
            System.out.println(e.getMessage());
        }
        return bookings;
    }
    public ArrayList<BookingSchedule> checkSlotBooked(String id ){
        ArrayList<BookingSchedule> bsCheck = new ArrayList<>();
        String sql = "  SELECT Date , slot_id FROM Booking_Schedule bs \n" +
                "                         JOIN Booking b ON bs.booking_id=b.id\n" +
                "                          JOIN Schedule s ON s.id = bs.schedule_id\n" +
                "               JOIN Mentor_Schedule ms ON ms.id = s.mentor_schedule_id\n" +
                "                          WHERE (b.status_id = 1 OR b.status_id=11) AND ms.mentor_id= ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                BookingSchedule bs = new BookingSchedule();
                Schedule sche = new Schedule();
                sche.setDate(resultSet.getDate("Date"));
                Slot s = new Slot();
                s.setId(resultSet.getInt("slot_id"));
                sche.setSlot(s);
                bs.setSchedule(sche);
                bsCheck.add(bs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bsCheck;
    }
    public int getIdSCheduleLogs(Date date , int slot_id,int mentor_schedule_id){
        String sql ="  SELECT id FROM Schedule_Logs\n" +
                "  WHERE Date = ? AND slot_id= ? AND mentor_schedule_id =? ";
        ArrayList<Schedule> schedules = new ArrayList<>();
        int id = 0;
        try {
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setDate(1, date);
            stm.setInt(2, slot_id);
            stm.setInt(3,mentor_schedule_id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                id=resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public void updateStatusScheduleLogs(int scheduleLogsId,int status_id){
        Connection connection = JDBC.getConnection();
        String sql = "UPDATE Schedule_Logs SET status_id = ?\n" +
                "  WHERE id = ?\n ";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status_id);
            stm.setInt(2, scheduleLogsId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<BookingSchedule> checkSlotBookedByRejecSlot(int booking_id ){
        ArrayList<BookingSchedule> bsCheck = new ArrayList<>();
        String sql = "  SELECT Date , slot_id FROM Booking_Schedule bs \n" +
                "                         JOIN Booking b ON bs.booking_id=b.id\n" +
                "                          JOIN Schedule s ON s.id = bs.schedule_id\n" +
                "               JOIN Mentor_Schedule ms ON ms.id = s.mentor_schedule_id\n" +
                "                          WHERE (b.status_id = 1 OR b.status_id=11) AND b.id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, booking_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BookingSchedule bs = new BookingSchedule();
                Schedule sche = new Schedule();
                sche.setDate(resultSet.getDate("Date"));
                Slot s = new Slot();
                s.setId(resultSet.getInt("slot_id"));
                sche.setSlot(s);
                bs.setSchedule(sche);
                bsCheck.add(bs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bsCheck;
    }
 }
