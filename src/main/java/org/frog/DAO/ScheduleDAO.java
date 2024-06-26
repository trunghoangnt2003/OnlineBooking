package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.*;
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
        String sql="SELECT s.id,s.date,slot_id,s.mentor_schedule_id,ms.mentor_id,bs.booking_id,skill.name,skill.src_icon,lvl.type,bs.schedule_id,isAtend,bs.status_id \n" +
                "                FROM Schedule s \n" +
                "INNER JOIN Mentor_Schedule ms ON s.mentor_schedule_id = ms.id \n" +
                "                LEFT JOIN Booking_Schedule bs ON s.id = bs.schedule_id\n" +
                "                LEFT JOIN Booking b ON b.id = bs.booking_id\n" +
                "               LEFT JOIN Level_Skill ls ON ls.id = b.level_skill_id\n" +
                "                LEFT JOIN Skill skill ON skill.id = ls.skill_id\n" +
                "\t\t\t\tLEFT JOIN Level lvl ON lvl.id=ls.level_id\n" +
                "                WHERE ms.mentor_id=? AND s.date >= ? AND s.date <= ?\n" +
                "                ORDER BY s.date ";
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


                Mentor_Schedule ms = new Mentor_Schedule();
                ms.setId(resultSet.getInt("mentor_schedule_id"));
                Mentor m = new Mentor();
                Account acc = new Account();
                acc.setId(resultSet.getString("mentor_id"));
                m.setAccount(acc);
                ms.setMentor(m);
                s.setMentorSchedule(ms);

                Slot sl = new Slot();
                sl.setId(resultSet.getInt("slot_id"));
                s.setSlot(sl);
                bs.setSchedule(s);

                Booking b = new Booking();
                Level_Skills ls = new Level_Skills();
                Skill skill = new Skill();
                skill.setName(resultSet.getString("name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));
                Level level = new Level();
                level.setName(resultSet.getString("type"));
                ls.setLevel(level);
                ls.setSkill(skill);
                b.setLevel_skills(ls);
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
    public int insertDayFreeByMentor(int id , Date date ,int slot_id){
        int numUppdate = 0;
        String sql="INSERT INTO [dbo].[Schedule]\n" +
                "           ([date]\n" +
                "           ,[slot_id]\n" +
                "           ,[mentor_schedule_id])\n" +
                "     VALUES\n" +
                "           (?\n" +
                "           ,?\n" +
                "           ,?)\n";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);

            preparedStatement.setInt(2, slot_id);
            preparedStatement.setInt(3, id);
            numUppdate = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numUppdate;
    }
    public void deleteDayFreeByMentor(int id , Date date ,int slot_id){
        String sql="DELETE FROM [dbo].[Schedule]\n" +
                "      WHERE  mentor_schedule_id=? AND date = ? AND slot_id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, slot_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getProccessingScheduleByMentorId (String id){
        try{
            String sql = " SELECT Count(Schedule_Logs.id) as totalProcess\n" +
                    "\t\t\t\t  FROM Schedule_Logs\n" +
                    "\t\t\t\t  INNER JOIN Mentor_Schedule on Schedule_Logs.mentor_schedule_id = Mentor_Schedule.id\n" +
                    "\t\t\t\t  WHERE mentor_id = ?  and status_id = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, StatusEnum.PROCESSING);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("totalProcess");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Schedule> getScheduleLogsByMentor(String id, Date from , Date to){
        ArrayList<Schedule> schedules = new ArrayList<>();
        String sql=" SELECT Schedule_Logs.id, Schedule_Logs.date, Schedule_Logs.slot_id, Schedule_Logs.mentor_schedule_id,Mentor_Schedule.mentor_id, Schedule_Logs.status_id, Slot.time_start, Slot.time_end, Status.type\n" +
                "FROM Schedule_Logs INNER JOIN Mentor_Schedule on Schedule_Logs.mentor_schedule_id = Mentor_Schedule.id\n" +
                "INNER JOIN Slot ON Schedule_Logs.slot_id = Slot.id\n" +
                "INNER JOIN Status ON Schedule_Logs.status_id = Status.id\n" +
                "WHERE mentor_id = ? and  [date] >= ? And  [date] <= ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setDate(2, from);
            preparedStatement.setDate(3, to);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDate(resultSet.getDate("date"));


                Mentor_Schedule ms = new Mentor_Schedule();
                ms.setId(resultSet.getInt("mentor_schedule_id"));
                Mentor m = new Mentor();
                Account acc = new Account();
                acc.setId(resultSet.getString("mentor_id"));
                m.setAccount(acc);
                ms.setMentor(m);
                schedule.setMentorSchedule(ms);

                Status st = new Status();
                st.setId(resultSet.getInt("status_id"));
                st.setType(resultSet.getString("type"));
                schedule.setStatus(st);

                Slot sl = new Slot();
                sl.setId(resultSet.getInt("slot_id"));
                sl.setStart_at(resultSet.getString("time_start"));
                sl.setEnd_at(resultSet.getString("time_end"));
                schedule.setSlot(sl);
                schedules.add(schedule);
            }
            return schedules;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    public ArrayList<Schedule> getAllScheduleLogsByMentor(String id){
        ArrayList<Schedule> schedules = new ArrayList<>();
        String sql="\n" +
                "SELECT Schedule_Logs.id, Schedule_Logs.date, Schedule_Logs.slot_id, Schedule_Logs.mentor_schedule_id,Mentor_Schedule.mentor_id, Schedule_Logs.status_id, Slot.time_start, Slot.time_end, Status.type\n" +
                "FROM     Schedule_Logs INNER JOIN\n" +
                "Mentor_Schedule on Schedule_Logs.mentor_schedule_id = Mentor_Schedule.id INNER JOIN\n"+
                "Slot ON Schedule_Logs.slot_id = Slot.id INNER JOIN\n" +
                "Status ON Schedule_Logs.status_id = Status.id\n" +
                "WHERE mentor_id = ? and status_id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, StatusEnum.PROCESSING);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDate(resultSet.getDate("date"));



                Mentor_Schedule ms = new Mentor_Schedule();
                ms.setId(resultSet.getInt("mentor_schedule_id"));
                Mentor m = new Mentor();
                Account acc = new Account();
                acc.setId(resultSet.getString("mentor_id"));
                m.setAccount(acc);
                ms.setMentor(m);
                schedule.setMentorSchedule(ms);

                Status st = new Status();
                st.setId(resultSet.getInt("status_id"));
                st.setType(resultSet.getString("type"));
                schedule.setStatus(st);

                Slot sl = new Slot();
                sl.setId(resultSet.getInt("slot_id"));
                sl.setStart_at(resultSet.getString("time_start"));
                sl.setEnd_at(resultSet.getString("time_end"));
                schedule.setSlot(sl);
                schedules.add(schedule);
            }
            return schedules;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    public  Schedule getScheduleLogs(String id){
        String sql="SELECT [id]\n" +
                "      ,[date]\n" +
                "      ,[slot_id]\n" +
                "      ,[mentor_schedule_id]\n" +
                "      ,[status_id]\n" +
                "  FROM [dbo].[Schedule_Logs]";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDate(resultSet.getDate("date"));

                Status st = new Status();
                st.setId(resultSet.getInt("status_id"));
                schedule.setStatus(st);

                Slot sl = new Slot();
                sl.setId(resultSet.getInt("slot_id"));
                schedule.setSlot(sl);

                Mentor_Schedule ms = new Mentor_Schedule();
                ms.setId(resultSet.getInt("mentor_schedule_id"));
                schedule.setMentorSchedule(ms);
                return schedule;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public  void updateById(int id, int status_id){
        String sql="UPDATE [dbo].[Schedule_Logs]\n" +
                "   SET [status_id] = ?\n" +
                " WHERE id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status_id);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insert(Schedule schedule){
        String sql="INSERT INTO [dbo].[Schedule]\n" +
                "           ([date]\n" +
                "           ,[slot_id]\n" +
                "           ,[mentor_schedule_id])\n" +
                "     VALUES\n" +
                "           (?\n" +
                "           ,?\n" +
                "           ,?)";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, schedule.getDate());
            preparedStatement.setInt(2, schedule.getSlot().getId());
            preparedStatement.setInt(3, schedule.getMentorSchedule().getId());
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Schedule> getLogsAllByMentorScheduleId(int id){
        ArrayList<Schedule> schedules = new ArrayList<>();
        String sql="SELECT * FROM Schedule_Logs WHERE mentor_schedule_id = ? And status_id = ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, StatusEnum.PROCESSING);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDate(resultSet.getDate("date"));

                Slot slot    = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                schedule.setSlot(slot);

                Status st = new Status();
                st.setId(resultSet.getInt("status_id"));
                schedule.setStatus(st);

                Mentor_Schedule ms = new Mentor_Schedule();
                ms.setId(resultSet.getInt("mentor_schedule_id"));
                schedule.setMentorSchedule(ms);

                schedules.add(schedule);
            }
            return schedules;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
