package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Booking_ScheduleDAO {

    public ArrayList<BookingSchedule> getBookingScheduleByMentor(String mentor_id){
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = " Select\ts.id as schedule_id, bs.id,bs.status_id,s.account_id as mentor_id,s.date,s.slot_id, bs.booking_id,\n" +
                    "\t\tb.level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type,\n" +
                    "\t\tb.id as booking_id, b.mentee_id as mentee_id\n" +
                    "From Schedule s Left Join Booking_Schedule bs  on  s.id = bs.schedule_id\n" +
                    "\t\tLeft Join Booking b ON bs.booking_id = b.id \n" +
                    "\t\tLeft Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                    "\t\tLeft Join [Level] l On ls.level_id = l.id\n" +
                    "\t\tLeft Join Skill sk On ls.skill_id = sk.id\n" +
                    "Where s.account_id = ?";
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

                Mentor mentor = new Mentor();
                Account account_mentor = new Account();
                account_mentor.setId(resultSet.getString("mentor_id"));
                mentor.setAccount(account_mentor);
                schedule.setMentor(mentor);

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

                Level level = new Level();
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
}
