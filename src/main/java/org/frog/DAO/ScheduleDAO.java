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
            String sql = "select Account.name as mentor_name ,Schedule.date, Skill.name, Level.type,Slot.id,Slot.time_start,Slot.time_end,Skill.id as skill_id,Skill.src_icon,Booking_Schedule.isAtend , Booking_Schedule.status_id from Booking\n" +
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
                skill.setId(resultSet.getInt("skill_id"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        ArrayList<BookingSchedule> schedules = scheduleDAO.getAllBookSchedule("ME699388");
        for (BookingSchedule bookingSchedule : schedules) {
            System.out.println(bookingSchedule.getBooking().getLevel_skills().getSkill().getSrc_icon());
        }
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

    public boolean checkDayExistScheduleLogs(int id , Date date , int slot_id){
        String sql="SELECT * FROM Schedule_Logs \n" +
                "WHERE mentor_schedule_id= ? AND date = ? AND slot_id = ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, slot_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int insertDayFreeByMentor(int id , Date date ,int slot_id){
        int numUppdate = 0;
        String sql="INSERT INTO [Schedule_Logs]\n" +
                "           ([date]\n" +
                "           ,[slot_id]\n" +
                "           ,[status_id]\n" +
                "           ,[mentor_schedule_id])\n" +
                "     VALUES\n" +
                "           (?\n" +
                "           ,?\n" +
                "           ,16\n" +
                "           ,?)";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(2, slot_id);
            numUppdate = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numUppdate;
    }


    public void reMarkDayFreeByMentor(int id , Date date ,int slot_id,int status_id){
        String sql="UPDATE [Schedule_Logs]\n" +
                "   SET \n" +
                "      [status_id] = ?\n" +
                " WHERE  mentor_schedule_id=? AND date = ? AND slot_id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status_id);
            preparedStatement.setInt(2, id);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, slot_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDayFreeByMentor(int id , Date date ,int slot_id,int status_id){
        String sql="UPDATE [dbo].[Schedule_Logs]\n" +
                "   SET \n" +
                "      [status_id] = ?\n" +
                " WHERE [mentor_schedule_id] = ? AND date = ? AND slot_id = ?  ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status_id);
            preparedStatement.setInt(2, id);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, slot_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Schedule> getScheduleLogsByMentorSet(String id) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        String sql = "\n" +
                "SELECT Schedule_Logs.id, Schedule_Logs.date, Schedule_Logs.slot_id, Schedule_Logs.mentor_schedule_id,Mentor_Schedule.mentor_id, Schedule_Logs.status_id, Slot.time_start, Slot.time_end, Status.type\n" +
                "FROM     Schedule_Logs INNER JOIN\n" +
                "Mentor_Schedule on Schedule_Logs.mentor_schedule_id = Mentor_Schedule.id INNER JOIN\n" +
                "Slot ON Schedule_Logs.slot_id = Slot.id INNER JOIN\n" +
                "Status ON Schedule_Logs.status_id = Status.id\n" +
                "WHERE mentor_id = ? ";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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


    public boolean isSlotAccepted(int id , Date date , int slot_id){
        String sql = "SELECT id FROM Schedule_Logs\n" +
                "WHERE mentor_schedule_id = ? AND date = ? AND slot_id = ? AND status_id = 11";
        try {
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,id);
            stm.setDate(2,date);
            stm.setInt(3,slot_id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void deleteSlotAccepted(int mentor_schedule_id , Date date , int slot_id){
        String sql ="DELETE FROM [Schedule]\n" +
                "WHERE mentor_schedule_id = ? AND date = ? AND slot_id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,mentor_schedule_id);
            stm.setDate(2,date);
            stm.setInt(3,slot_id);
            stm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  boolean checkLastSlot(int booking_id){
        String sql ="WITH BookingCounts AS (\n" +
                "    SELECT \n" +
                "        booking_id,\n" +
                "        COUNT(isAtend) AS CountIsAtend,\n" +
                "        COUNT(id) AS CountBookingSlot\n" +
                "    FROM Booking_Schedule\n" +
                "    WHERE booking_id = ?\n" +
                "    GROUP BY booking_id\n" +
                ")\n" +
                "SELECT booking_id\n" +
                "FROM BookingCounts\n" +
                "WHERE CountIsAtend = CountBookingSlot;";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,booking_id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Schedule> getScheduleLogsByMentor(String id, Date from , Date to){
        ArrayList<Schedule> schedules = new ArrayList<>();
        String sql=" SELECT Schedule_Logs.id, Schedule_Logs.date, Schedule_Logs.slot_id, Schedule_Logs.mentor_schedule_id,Mentor_Schedule.mentor_id, Schedule_Logs.status_id, Slot.time_start, Slot.time_end, Status.type\n" +
                "FROM Schedule_Logs INNER JOIN Mentor_Schedule on Schedule_Logs.mentor_schedule_id = Mentor_Schedule.id\n" +
                "INNER JOIN Slot ON Schedule_Logs.slot_id = Slot.id\n" +
                "INNER JOIN Status ON Schedule_Logs.status_id = Status.id\n" +
                "WHERE mentor_id = ? and  [date] >= ? And  [date] <= ? AND status_id != ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setDate(2, from);
            preparedStatement.setDate(3, to);
            preparedStatement.setInt(4, StatusEnum.CANCEL);
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

    public  void updateLogsById(int id, int status_id){
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

    public ArrayList<Schedule> getLogsProcessByMentorScheduleId(int id){
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
    public int checkProcessStatusScheduleLogs(int mentor_schedule_id, Date date , int slot_id){
        String sql = "SELECT status_id FROM Schedule_Logs\n" +
                "\tWHERE mentor_schedule_id = ? AND date = ? AND slot_id = ?";
        int num = 0;
        try {
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,mentor_schedule_id);
            stm.setDate(2,date);
            stm.setInt(3,slot_id);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
               num = resultSet.getInt("status_id");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;

    }

    public ArrayList<Schedule> getLogsWaitCancelByMentorScheduleId(int id){
        ArrayList<Schedule> schedules = new ArrayList<>();
        String sql="SELECT * FROM Schedule_Logs WHERE mentor_schedule_id = ? And status_id = ?";
        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, StatusEnum.WAITCANCEL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDate(resultSet.getDate("date"));

                Slot slot = new Slot();
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

    public Schedule getScheduleByInfo(int mentor_schedule_id , Date date , int slot_id){
        try{
            String sql = "SELECT * FROM [dbo].[Schedule]\n" +
                    "WHERE mentor_schedule_id = ? AND date = ? AND slot_id = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,mentor_schedule_id);
            preparedStatement.setDate(2,date);
            preparedStatement.setInt(3,slot_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setDate(resultSet.getDate("date"));

                Slot slot = new Slot();
                slot.setId(resultSet.getInt("slot_id"));
                schedule.setSlot(slot);

                Mentor_Schedule ms = new Mentor_Schedule();
                ms.setId(resultSet.getInt("mentor_schedule_id"));
                schedule.setMentorSchedule(ms);
                return schedule;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String messageSchedule(String id){
        String message = "";
        String sql = " SELECT [message]  FROM [Mentor_Schedule]\n" +
                "  WHERE mentor_id =? ";
        PreparedStatement stm = null;
        try {
            stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()){
                message = resultSet.getString("message");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return message;
    }
    public  void deleteMessageSchedule(String id){
        String sql = "UPDATE [Mentor_Schedule]\n" +
                "   SET\n" +
                "      [message] = NULL\n" +
                "  WHERE mentor_id = ? ";
        try {
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

