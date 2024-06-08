package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ScheduleDAO {
    public ArrayList<BookingSchedule> getAllBookSchedule(String mentee_Id) {
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Account.name as mentor_name ,Schedule.date, Skill.name, Level.type,Slot.id,Slot.time_start,Slot.time_end,Skill.src_icon,Booking_Schedule.isAtend , Booking_Schedule.status_id from Booking\n" +
                    "\n" +
                    "                    join Booking_Schedule\n" +
                    "                    on Booking.id = Booking_Schedule.booking_id\n" +
                    "                    join Mentor\n" +
                    "                    on Mentor.account_id = Booking.mentor_id\n" +
                    "                    join Account\n" +
                    "                    on Account.id = Mentor.account_id\n" +
                    "                    join Level_Skill\n" +
                    "                    on Booking.level_skill_id = Level_Skill.id\n" +
                    "                    join Skill\n" +
                    "                    on Skill.id = Level_Skill.skill_id\n" +
                    "                    join Level\n" +
                    "                    on Level.id = Level_Skill.level_id\n" +
                    "                    join Schedule\n" +
                    "                    on Booking_Schedule.schedule_id = Schedule.id\n" +
                    "                    join Slot\n" +
                    "                    on Schedule.slot_id = Slot.id\n" +
                    "                    where Booking.mentee_id = ? and ((Booking.status_id = ?) or (Booking.status_id = ?))";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentee_Id);
            preparedStatement.setInt(2, StatusEnum.DONE);
            preparedStatement.setInt(3, StatusEnum.ACCEPTED);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookingSchedule bookingSchedule = new BookingSchedule();

                Account account = new Account();
                account.setName(resultSet.getString("mentor_name"));
                Mentor mentor = new Mentor();
                mentor.setAccount(account);


                Skill skill = new Skill();
                skill.setName(resultSet.getString("name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));

                Level level = new Level();
                level.setName(resultSet.getString("type"));

                Level_Skills level_skills = new Level_Skills();
                level_skills.setLevel(level);
                level_skills.setSkill(skill);


                Slot slot = new Slot();
                slot.setId(resultSet.getInt("id"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));

                Schedule schedule = new Schedule();
                schedule.setDate(resultSet.getDate("date"));
                schedule.setSlot(slot);
                bookingSchedule.setSchedule(schedule);

                Booking booking = new Booking();
                booking.setMentor(mentor);
                booking.setLevel_skills(level_skills);

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                booking.setStatus(status);

                bookingSchedule.setAttend(resultSet.getBoolean("isAtend"));
                bookingSchedule.setStatus(status);
                bookingSchedule.setBooking(booking);
                list.add(bookingSchedule);


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<BookingSchedule> allBookSchedule = scheduleDAO.getAllBookSchedule("e8fd47ed-dec2-49bf-829c-b182230ea49d");

        for (BookingSchedule bookingSchedule : allBookSchedule) {
            System.out.println(bookingSchedule.getSchedule().getSlot().getId());
            System.out.println(bookingSchedule.getSchedule().getSlot().getStart_at());
            System.out.println(bookingSchedule.getSchedule().getDate());
        }
    }
}
