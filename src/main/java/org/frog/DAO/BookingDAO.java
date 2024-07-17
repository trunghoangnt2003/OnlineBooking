package org.frog.DAO;

import com.sun.jdi.PathSearchingVirtualMachine;
import org.frog.model.*;
import org.frog.utility.DateTimeHelper;
import org.frog.utility.StatusEnum;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDAO {
    public ArrayList<Booking> estimatedIncome(String dateFrom,String dateTo){
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT CAST(create_date AS DATE) as date, SUM(amount) as total_amount\n" +
                    "FROM [dbo].[Booking]\n" +
                    "where Booking.status_id = 3 or Booking.status_id = 11\n" +
                    "GROUP BY CAST(create_date AS DATE)\n" +
                    "ORDER BY date ASC;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setDate(resultSet.getTimestamp(1));
                booking.setAmount(resultSet.getInt(2));
                bookings.add(booking);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    public int countBookingByStatus(int status) {
        try {

            Connection connection = JDBC.getConnection();
            String sql = "SELECT Count(*)\n" +
                    "FROM [Prog_DB].[dbo].[Booking]\n" +
                    "where status_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int CalcBookByMentor(String mentorId) {
        try {

            Connection connection = JDBC.getConnection();
            String sql = "Select Count(mentee_id) as number_booking\n" +
                    "From Booking \n" +
                    "Where mentor_id = ? and status_id = 3";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number_booking");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<BookingSchedule> getBookingScheduleById(int id) {
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try {
        Connection connection = JDBC.getConnection();
        String sql = "select Booking.id as bId, Slot.id as sId,Booking_Schedule.isAtend, Slot.time_start,Slot.time_end,Schedule.date,Skill.src_icon, Level.type as lvType,Status.id,Status.type as stType from Booking join Booking_Schedule\n" +
                "on Booking_Schedule.booking_id = Booking.id\n" +
                "join Schedule\n" +
                "on Booking_Schedule.schedule_id = Schedule.id\n" +
                "join Slot\n" +
                "on\n" +
                "Schedule.slot_id = Slot.id\n" +
                "join Level_Skill\n" +
                "on Booking.level_skill_id = Level_Skill.id\n" +
                "join Skill\n" +
                "on Level_Skill.skill_id = Skill.id\n" +
                "join Level\n" +
                "on Level_Skill.level_id = Level.id\n" +
                "join Status\n" +
                "on Status.id = Booking.status_id\n" +
                "where Booking_Schedule.booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Slot slot = new Slot();
                slot.setId(resultSet.getInt("sId"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));

                Schedule schedule = new Schedule();
                schedule.setSlot(slot);
                schedule.setDate(Date.valueOf(resultSet.getString("date")));

                Level level = new Level();
                level.setName(resultSet.getString("lvType"));
                Skill skill = new Skill();
                skill.setSrc_icon(resultSet.getString("src_icon"));

                Level_Skills level_skills = new Level_Skills();
                level_skills.setLevel(level);
                level_skills.setSkill(skill);

                Status status = new Status();
                status.setId(resultSet.getInt("id"));
                status.setType(resultSet.getString("stType"));



                Booking booking = new Booking();
                booking.setId(resultSet.getInt("bId"));
                booking.setLevel_skills(level_skills);
                booking.setStatus(status);

                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setSchedule(schedule);
                bookingSchedule.setBooking(booking);
                bookingSchedule.setAttend(resultSet.getBoolean("isAtend"));
               list.add(bookingSchedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Booking> getAllRequestProcessOfBooking( String id) {
        List<Booking> bookingList = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name],Booking.id,Booking.mentor_id ,Booking.amount, Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date \n" +
                    "                                        from Booking join [dbo].[Level_Skill]\n" +
                    "                                        on Booking.level_skill_id = [dbo].[Level_Skill].id\n" +
                    "                                        join [dbo].[Level]\n" +
                    "                                        on [dbo].[Level_Skill].level_id=[dbo].[Level].id\n" +
                    "                                        join Skill \n" +
                    "                                        on Skill.id = [dbo].[Level_Skill].skill_id\n" +
                    "                                        join Mentor\n" +
                    "                                       on Booking.mentor_id = Mentor.account_id\n" +
                    "                                        join Account\n" +
                    "                                        on Account.id = Mentor.account_id\n" +
                    "                    where Booking.mentee_id = ? and Booking.status_id = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                account.setId(resultSet.getString("mentor_id"));
                Mentor mentor = new Mentor(account);
                Skill skill = new Skill();
                skill.setName(resultSet.getString("skill"));
                Level level = new Level();
                level.setName(resultSet.getString("type"));
                Level_Skills levelSkills = new Level_Skills();
                levelSkills.setSkill(skill);
                levelSkills.setLevel(level);

                Booking booking = new Booking();
                booking.setMentor(mentor);
                booking.setLevel_skills(levelSkills);
                booking.setAmount(resultSet.getInt("amount"));
                booking.setId(resultSet.getInt("id"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setDate(resultSet.getTimestamp("create_date"));

                bookingList.add(booking);

            }
            return bookingList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertBooking(Booking booking) {

        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Booking]\n" +
                    "           ([status_id]\n" +
                    "           ,[amount]\n" +
                    "           ,[create_date]\n" +
                    "           ,[mentor_id]\n" +
                    "           ,[mentee_id]\n" +
                    "           ,[from_date]\n" +
                    "           ,[to_date]\n" +
                    "           ,[description]\n" +
                    "           ,[level_skill_id]\n" +
                    "           ,[total_slot])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,GETDATE()\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, booking.getStatus().getId());
            preparedStatement.setInt(2, booking.getAmount());
            preparedStatement.setString(3, booking.getMentor().getAccount().getId());
            preparedStatement.setString(4, booking.getMentee().getAccount().getId());
            preparedStatement.setDate(5, booking.getStartDate());
            preparedStatement.setDate(6, booking.getEndDate());
            preparedStatement.setString(7, booking.getDescription());
            preparedStatement.setInt(8, booking.getLevel_skills().getId());
            preparedStatement.setInt(9, booking.getTotalSlot());
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BookingSchedule> getBookingScheduleLogById(int id) {
        ArrayList<BookingSchedule> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Booking.id as bId, Slot.id as sId,Schedule_Booking_Logs.status_id,Booking.status_id as bStatus, Slot.time_start,Slot.time_end,Schedule.date,Skill.src_icon, Level.type as lvType,Status.type as stType from Booking join Schedule_Booking_Logs\n" +
                    "on Schedule_Booking_Logs.booking_id = Booking.id\n" +
                    "join Schedule\n" +
                    "on Schedule_Booking_Logs.schedule_id = Schedule.id\n" +
                    "join Slot\n" +
                    "on\n" +
                    "Schedule.slot_id = Slot.id\n" +
                    "join Level_Skill\n" +
                    "on Booking.level_skill_id = Level_Skill.id\n" +
                    "join Skill\n" +
                    "on Level_Skill.skill_id = Skill.id\n" +
                    "join Level\n" +
                    "on Level_Skill.level_id = Level.id\n" +
                    "join Status\n" +
                    "on Status.id = Booking.status_id\n" +
                    "where Schedule_Booking_Logs.booking_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Slot slot = new Slot();
                slot.setId(resultSet.getInt("sId"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));

                Schedule schedule = new Schedule();
                schedule.setSlot(slot);
                schedule.setDate(Date.valueOf(resultSet.getString("date")));

                Level level = new Level();
                level.setName(resultSet.getString("lvType"));
                Skill skill = new Skill();
                skill.setSrc_icon(resultSet.getString("src_icon"));

                Level_Skills level_skills = new Level_Skills();
                level_skills.setLevel(level);
                level_skills.setSkill(skill);

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("stType"));



                Booking booking = new Booking();
                booking.setId(resultSet.getInt("bId"));
                booking.setLevel_skills(level_skills);
                booking.setStatus(status);

                BookingSchedule bookingSchedule = new BookingSchedule();
                bookingSchedule.setSchedule(schedule);
                bookingSchedule.setBooking(booking);
                list.add(bookingSchedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Booking findByInfo(Booking b) {
        Booking booking = null;

        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT TOP 1 id, create_date " +
                    "FROM Booking " +
                    "WHERE mentor_id = ? AND mentee_id = ? AND from_date = ? AND to_date = ? AND level_skill_id = ? " +
                    "ORDER BY create_date DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, b.getMentor().getAccount().getId());
            preparedStatement.setString(2, b.getMentee().getAccount().getId());
            preparedStatement.setDate(3, b.getStartDate());
            preparedStatement.setDate(4, b.getEndDate());
            preparedStatement.setInt(5, b.getLevel_skills().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setStatus(b.getStatus());
                booking.setAmount(b.getAmount());
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setMentor(b.getMentor());
                booking.setMentee(b.getMentee());
                booking.setStartDate(b.getStartDate());
                booking.setEndDate(b.getEndDate());
                booking.setDescription(b.getDescription());
                booking.setLevel_skills(b.getLevel_skills());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    public ArrayList<Booking> getHistoryDoneAndAccept(String mentee_id ){
        ArrayList<Booking> bookingList = new ArrayList<>();

        try{

            Connection connection = JDBC.getConnection();
            String sql = "SELECT \n" +
                    "    Booking.id, \n" +
                    "\tBooking.status_id,\n" +
                    "\ts.type,\n" +
                    "    Booking.amount, \n" +
                    "    Booking.create_date,\n" +
                    "    Booking.mentor_id, \n" +
                    "    Booking.mentee_id, \n" +
                    "    Booking.from_date, \n" +
                    "    Booking.to_date,\n" +
                    "    Booking.level_skill_id, \n" +
                    "    Level_Skill.skill_id, \n" +
                    "    Skill.name AS skill_name, \n" +
                    "    Skill.src_icon, \n" +
                    "    Level_Skill.level_id, \n" +
                    "    [Level].[type], \n" +
                    "    Account.name AS mentor_name, \n" +
                    "    Account.mail\n" +
                    "FROM  \n" +
                    "    Booking \n" +
                    "INNER JOIN\n" +
                    "    Mentor ON Booking.mentor_id = Mentor.account_id \n" +
                    "INNER JOIN\n" +
                    "    Account ON Mentor.account_id = Account.id\n" +
                    "JOIN \n" +
                    "    Level_Skill ON Booking.level_skill_id = Level_Skill.id\n" +
                    "JOIN \n" +
                    "    Skill ON Level_Skill.skill_id = Skill.id \n" +
                    "JOIN \n" +
                    "    [Level] ON Level_Skill.level_id = [Level].id  \n" +
                    "JOIN Status s ON Booking.status_id = s.id\n" +
                    "WHERE \n" +
                    "    (Booking.status_id = ? OR Booking.status_id = ? OR Booking.status_id = ?) \n" +
                    "    AND Booking.mentee_id = ?\n" +
                    "ORDER BY \n" +
                    "    create_date DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.DONE);
            preparedStatement.setInt(2, StatusEnum.ACCEPTED);
            preparedStatement.setInt(3, StatusEnum.PAID);
            preparedStatement.setString(4, mentee_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id"));
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("type"));
                booking.setStatus(status);

                Account acc_mentor = new Account();
                acc_mentor.setId(resultSet.getString("mentor_id"));
                acc_mentor.setName(resultSet.getString("mentor_name"));
                acc_mentor.setEmail(resultSet.getString("mail"));
                Mentor mentor = new Mentor();
                mentor.setAccount(acc_mentor);
                booking.setMentor(mentor);


                Account acc_mentee = new Account();
                acc_mentee.setId(resultSet.getString("mentee_id"));
                Mentee mentee = new Mentee();
                mentee.setAccount(acc_mentee);
                booking.setMentee(mentee);

                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));

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

                bookingList.add(booking);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return bookingList;
    }

    public List<Booking> getAllRequestCancelOfBooking( String id) {
        List<Booking> bookingList = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "select Account.[name],Booking.mentor_id,Status.type as status_Name,Booking.amount,Booking.id, Booking.from_date, Booking.to_date, Booking.[description], Skill.[name] as skill,Level.type,Booking.create_date \n" +
                    "                                                           from Booking join [dbo].[Level_Skill]\n" +
                    "                                                          on Booking.level_skill_id = [dbo].[Level_Skill].id\n" +
                    "                                                            join [dbo].[Level]\n" +
                    "                                                            on [dbo].[Level_Skill].level_id=[dbo].[Level].id\n" +
                    "                                                            join Skill \n" +
                    "                                                            on Skill.id = [dbo].[Level_Skill].skill_id\n" +
                    "                                                           join Mentor\n" +
                    "                                                          on Booking.mentor_id = Mentor.account_id\n" +
                    "                                                            join Account\n" +
                    "                                                            on Account.id = Mentor.account_id\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tjoin Status\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\ton Status.id = Booking.status_id\n" +
                    "                                        where Booking.mentee_id = ? and ((Booking.status_id = 2) or (Booking.status_id = 12))"+
                    "  Order By create_date desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("name"));
                account.setId(resultSet.getString("mentor_id"));
                Mentor mentor = new Mentor(account);

                Skill skill = new Skill();
                skill.setName(resultSet.getString("skill"));
                Level level = new Level();
                level.setName(resultSet.getString("type"));
                Level_Skills levelSkills = new Level_Skills();
                levelSkills.setSkill(skill);
                levelSkills.setLevel(level);

                Status status = new Status();
                status.setType(resultSet.getString("status_Name"));

                Booking booking = new Booking();
                booking.setStatus(status);
                booking.setMentor(mentor);
                booking.setLevel_skills(levelSkills);
                booking.setId(resultSet.getInt("id"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setAmount(resultSet.getInt("amount"));
                bookingList.add(booking);

            }
            return bookingList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cancelBooking(String id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Booking]\n" +
                    "   SET [status_id] = ?\n" +
                    "      ,[create_date] = GETDATE()\n" +
                    " WHERE Booking.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,StatusEnum.CANCEL);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Booking> getMyBookingByMentee(String id ){
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
                    "WHERE b.mentor_id = ? AND b.status_id = 11 OR b.status_id = 3 AND b.mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,id);

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
            e.printStackTrace();
        }
        return bookings;
    }
    public ArrayList<Booking> getAllBookingByMentee(){
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
                    "WHERE b.status_id = 3 OR b.status_id = 7";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
            e.printStackTrace();
        }
        return bookings;
    }
    public Booking getBookingById(int bookingid){
        Booking booking = new Booking();
        String sql = "Select  b.id,b.status_id,status.type,\n" +
                "\t\t\t\t\tb.amount,b.create_date,b.description,b.from_date,b.to_date,b.total_slot,\n" +
                "                    level_skill_id,skill_id, sk.name as skill_name, sk.src_icon, ls.level_id, l.type as typeS,b.mentee_id,b.mentor_id,\n" +
                "\t\t\t\t\tacc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone \n" +
                "FROM Booking b\n" +
                " INNER Join Level_Skill ls on b.level_skill_id = ls.id\n" +
                "                    INNER Join [Level] l On ls.level_id = l.id\n" +
                "                    INNER Join Skill sk On ls.skill_id = sk.id\n" +
                "\t\t\t\t\tINNER JOIN Mentee m ON b.mentee_id = m.account_id\n" +
                "\t\t\t\t\tINNER JOIN Account acc ON acc.id = m.account_id\n" +
                "\t\t\t\t\tINNER JOIN Status status ON status.id = b.status_id\n" +
                "\t\t\t\t\twhere b.id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,bookingid);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                status.setType(resultSet.getString("type"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("skill_name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));

                Level level = new Level();
                level.setId(resultSet.getInt("level_id"));
                level.setName(resultSet.getString("typeS"));

                Level_Skills level_skill = new Level_Skills();
                level_skill.setId(resultSet.getInt("level_skill_id"));
                level_skill.setSkill(skill);
                level_skill.setLevel(level);

                Account acc = new Account();
                acc.setId(resultSet.getString("mentee_id"));
                acc.setName(resultSet.getString("name"));

                Mentee mentee = new Mentee();
                mentee.setAccount(acc);

                Account accMentor = new Account();
                accMentor.setId(resultSet.getString("mentor_id"));

                Mentor mentor = new Mentor();
                mentor.setAccount(accMentor);
                mentor.setAccount(accMentor);

                booking.setStatus(status);
                booking.setMentee(mentee);
                booking.setMentor(mentor);
                booking.setLevel_skills(level_skill);
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setDescription(resultSet.getString("description"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                booking.setTotalSlot(resultSet.getInt("total_slot"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return booking;

    }


    public Map<String,Integer> statisticByMentee(String id){
        Map<String,Integer> statistic = new HashMap<>();
        try{
            String sql = "Select b.status_id,s.type,Count(b.status_id) as sum_per_req\n" +
                    "From Booking b JOIN [Status] s on b.status_id = s.id\n" +
                    "Where mentee_id = ?\n" +
                    "Group By b.status_id, s.type";

            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statistic.put(resultSet.getString("type"),resultSet.getInt("sum_per_req"));
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return statistic;
    }

    public int getTotalBookByMentee(String id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select count(Booking_Schedule.id) as number_slot\n" +
                    "From Booking JOIN Booking_Schedule ON Booking.id = Booking_Schedule.booking_id\n" +
                    "WHERE mentee_id = ?\n" +
                    "And (Booking_Schedule.status_id = ?\n" +
                    "or Booking_Schedule.status_id = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, StatusEnum.DONE);
            preparedStatement.setInt(3, StatusEnum.ACCEPTED);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number_slot");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int totalRequestBook(String id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select count(id) as total\n" +
                    "From Booking "+
                    "WHERE mentee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Booking getById(int id) {
        try{
            Connection connection = JDBC.getConnection();
            String sql = "Select * from Booking where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               Booking booking = new Booking();
               booking.setId(resultSet.getInt("id"));
               booking.setAmount(resultSet.getInt("amount"));
               booking.setDate(resultSet.getTimestamp("create_date"));
               booking.setStartDate(resultSet.getDate("from_date"));
               booking.setEndDate(resultSet.getDate("to_date"));
               booking.setDescription(resultSet.getString("description"));


               Account accMentee = new Account();
                accMentee.setId(resultSet.getString("mentee_id"));
                Mentee mentee = new Mentee(accMentee);
               booking.setMentee(mentee);

               Account accMenter = new Account();
               accMenter.setId(resultSet.getString("mentor_id"));
               Mentor mentor = new Mentor(accMenter);
               booking.setMentor(mentor);

               Level_Skills level_skills = new Level_Skills();
               level_skills.setId(resultSet.getInt("level_skill_id"));
               booking.setLevel_skills(level_skills);

               Status status = new Status();
               status.setId(resultSet.getInt("status_id"));
               booking.setStatus(status);

               return booking;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Booking> getBookingByName(String stDate, String endDate,int stId,String name, int offset, int limit) {
        if(name == null) name = "";
        List<Booking> requestListOfMentee = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT   Booking.id,\n" +
                    "                       AcMT.[name] AS nameMT, \n" +
                    "                        AcMO.[name] AS nameMO,Status.id AS statusId, \n" +
                    "                       Booking.description, \n" +
                    "                        Status.[type] AS statusType, \n" +
                    "                       Booking.amount,\n" +
                    "                        Booking.create_date, \n" +
                    "                        Level.type AS levelType, \n" +
                    "                        Skill.name \n" +
                    "                    from Booking\n" +
                    "join Status\n" +
                    "on Status.id = Booking.status_id\n" +
                    "join Mentor \n" +
                    "on Mentor.account_id = Booking.mentor_id\n" +
                    "join Account as AcMo\n" +
                    "on AcMo.id = Mentor.account_id\n" +
                    "join Mentee\n" +
                    "on Booking.mentee_id = Mentee.account_id\n" +
                    "join Account as AcMT\n" +
                    "on AcMT.id = Mentee.account_id\n" +
                    "join Level_Skill\n" +
                    "on Level_Skill.id = Booking.level_skill_id\n" +
                    "join Skill\n" +
                    "on Skill.id = Level_Skill.skill_id\n" +
                    "join Level\n" +
                    "on Level.id = Level_Skill.level_id\n" +
                    "                    WHERE \n" +
                    "                        (AcMT.[name] LIKE ? \n" +
                    "                        OR AcMO.[name] LIKE ?) ";

            if(stId != 0){
                sql = sql + " AND Status.id = " + stId;
            }
            if (stDate != null && endDate != null) {
               if(stDate != "" && endDate != ""){
                   sql = sql + " and Booking.create_date >= '" + stDate + "' and Booking.create_date <= '" + endDate + "'";
               }
            }
            sql += " ORDER BY Booking.create_date DESC \n" +
                    "                    OFFSET ? ROWS \n" +
                    "                    FETCH NEXT ? ROWS ONLY";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

               String searchPattern = "%" + name + "%";

            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setInt(3, offset);
            preparedStatement.setInt(4, limit);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setName(resultSet.getString("nameMT"));

                Account accountMentor = new Account();
                accountMentor.setName(resultSet.getString("nameMO"));

                Mentee mentee = new Mentee(account);
                mentee.setAccount(account);

                Mentor mentor = new Mentor();
                mentor.setAccount(accountMentor);

                Skill skill = new Skill();
                skill.setName(resultSet.getString("name"));
                Level level = new Level();
                level.setName(resultSet.getString("levelType"));
                Level_Skills level_skills = new Level_Skills();
                level_skills.setSkill(skill);
                level_skills.setLevel(level);

                Status status = new Status();
                status.setId(resultSet.getInt("statusId"));
                status.setType(resultSet.getString("statusType"));

                Booking booking = new Booking();
                booking.setLevel_skills(level_skills);
                booking.setMentor(mentor);
                booking.setStatus(status);
                booking.setMentee(mentee);
                booking.setAmount(resultSet.getInt("amount"));
                booking.setDate(resultSet.getTimestamp("create_date"));
                booking.setId(resultSet.getInt("id"));
                booking.setDescription(resultSet.getString("description"));
                requestListOfMentee.add(booking);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestListOfMentee;
    }

    public int getTotalBookingsCountByName(String stDate, String endDate,String name, int status) {
        if(name == null){
            name = "";
        }
        int total = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM Booking " +
                    "JOIN Mentee ON Mentee.account_id = Booking.mentee_id " +
                    "JOIN Account AS AccMT ON Mentee.account_id = AccMT.id " +
                    "JOIN Mentor ON Mentor.account_id = Booking.mentor_id " +
                    "JOIN Account AS AcMO ON Mentor.account_id = AcMO.id " +
                    "join Status\n" +
                    "on Status.id = Booking.status_id\n" +
                    "WHERE (AccMT.name LIKE ? OR AcMO.name LIKE ?)";
            if(status != 0){
                sql = sql + " AND Status.id = " + status;
            }
            if (stDate != null && endDate != null) {
                if(stDate != "" && endDate != ""){
                    sql = sql + " and Booking.create_date >= '" + stDate + "' and Booking.create_date <= '" + endDate + "'";
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String searchPattern = "%" + name + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt("total");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    public int findBookingIdByBookingScheduleId(int BookingScheduleId){
        String sql = "SELECT booking_id FROM Booking_Schedule\n" +
                "WHERE id = ?";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1,BookingScheduleId);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                return rs.getInt("booking_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean isNewBooking(String mentorId){
        String sql = "SELECT id FROM Booking\n" +
                "\tWHERE mentor_id = ? AND status_id = 1";
        try{
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setString(1,mentorId);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isBookingExpired(int bookingId) {
        Date date = null;
        String time = null;
        String sql = "SELECT MIN(from_date) AS Date, MIN(slot.time_start) AS Time FROM Booking b " +
                "INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id " +
                "INNER JOIN Schedule s ON s.id = bs.schedule_id " +
                "INNER JOIN Slot slot ON slot.id = s.slot_id " +
                "WHERE b.id = ?";

        try (PreparedStatement stm = JDBC.getConnection().prepareStatement(sql)) {
            stm.setInt(1, bookingId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                date = rs.getDate("Date");
                time = rs.getString("Time");
            }
            if (date != null && time != null) {
                Timestamp bookingTimestamp = DateTimeHelper.convertToTimestamp(date.toString(), time);
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                return bookingTimestamp.before(currentTimestamp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

