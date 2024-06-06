package org.frog.DAO;

import org.frog.model.*;

import java.sql.*;
import java.util.ArrayList;

public class MentorDAO {

    public ArrayList<Mentor> selectAll() {

        ArrayList<Mentor> list = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "Select *\n" +
                    "from Account a JOIN Mentor m on a.id = m.account_id ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setDob(resultSet.getDate("dob"));

                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor.setEducation(resultSet.getString("education"));
                mentor.setExperience(resultSet.getString("experience"));
                mentor.setPrice(resultSet.getInt("price"));
                mentor.setProfileDetail(resultSet.getString("profile_detail"));
                mentor.setRating(resultSet.getFloat("rating"));

                SkillsDAO skillsDAO = new SkillsDAO();
//                mentor.setSkills(  skillsDAO.getByMentorId(account.getId()));

                BookingDAO bookingDAO = new BookingDAO();

                mentor.setTotalBookings(bookingDAO.CalcBookByMentor(account.getId()));
                list.add(mentor);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Mentor> getMentorAndPaging(int page, String skill, String level) {
        ArrayList<Mentor> list = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "Select a.id,a.username,a.name,a.dob,a.avatar,m.experience,m.price,m.rating, s.name, l.type\n" +
                    "From Account a JOIN Mentor m on a.id = m.account_id\n" +
                    "\t JOIN Mentor_Level_Skill mls ON m.account_id = mls.mentor_id\n" +
                    "\t JOin Level_Skill ls on mls.skill_level_id = ls.id\n" +
                    "\t JOin Skill  s ON ls.skill_id = s.id\n" +
                    "\t Join Level l On ls.level_id = l.id\n" +
                    "Where s.name = ? And l.type = ?\n" +
                    "ORDER BY a.id\n" +
                    "OFFSET ? ROWS FETCH NEXT 4 ROWS ONLY";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, skill);
            preparedStatement.setString(2, level);
            preparedStatement.setInt(3, (page - 1) * 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setDob(resultSet.getDate("dob"));
                account.setAvatar(resultSet.getString("avatar"));

                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor.setExperience(resultSet.getString("experience"));
                mentor.setPrice(resultSet.getInt("price"));
                mentor.setRating(resultSet.getFloat("rating"));

                SkillsDAO skillsDAO = new SkillsDAO();
//                mentor.setSkills(  skillsDAO.getByMentorId(account.getId()));

                BookingDAO bookingDAO = new BookingDAO();
                mentor.setTotalBookings(bookingDAO.CalcBookByMentor(account.getId()));

                list.add(mentor);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public int totalMentor(String skill, String level) {
        int total = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select Count(m.account_id)\n" +
                    "From Account a JOIN Mentor m on a.id = m.account_id\n" +
                    "\t JOIN Mentor_Level_Skill mls ON m.account_id = mls.mentor_id\n" +
                    "\t JOin Level_Skill ls on mls.skill_level_id = ls.id\n" +
                    "\t JOin Skill  s ON ls.skill_id = s.id\n" +
                    "\t Join Level l On ls.level_id = l.id\n" +
                    "Where s.name = ? And l.type = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, skill);
            preparedStatement.setString(2, level);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void update(Mentor mentor) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Mentor]\n" +
                    "           ([account_id]\n" +
                    "           ,[profile_detail]\n" +
                    "           ,[price]\n" +
                    "           ,[experience]\n" +
                    "           ,[education])\n" +
                    "     VALUES (\n" +
                    "           ?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentor.getAccount().getId());
            preparedStatement.setString(2, mentor.getProfileDetail());
            preparedStatement.setInt(3, mentor.getPrice());
            preparedStatement.setString(4, mentor.getExperience());
            preparedStatement.setString(5, mentor.getEducation());
            preparedStatement.execute();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Mentor getMentorById(String id) {

        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Account.id, Account.name, Account.dob, Account.phone, Account.gender, " +
                    "Account.address, Account.mail,\n" +
                    "\t\tAccount.avatar, Mentor.profile_detail, Mentor.price,\n" +
                    "\t\tMentor.experience, Mentor.education, Mentor.rating\n" +
                    "\t\tFROM     Account INNER JOIN\n" +
                    "                  Mentor ON Account.id = Mentor.account_id\n" +
                    "\t\t\t\t  where Mentor.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setDob(resultSet.getDate("dob"));
                account.setPhone(resultSet.getString("phone"));
                account.setGender(resultSet.getInt("gender"));
                account.setAddress(resultSet.getString("address"));
                account.setEmail(resultSet.getString("mail"));
                account.setAvatar(resultSet.getString("avatar"));

                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor.setProfileDetail(resultSet.getString("profile_detail"));
                mentor.setPrice(resultSet.getInt("price"));
                mentor.setExperience(resultSet.getString("experience"));
                mentor.setEducation(resultSet.getString("education"));
                mentor.setRating(resultSet.getFloat("rating"));
                return mentor;
            }
            JDBC.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

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
            String sql = "SELECT b.id,b.mentee_id,create_date,amount,from_date,to_date,b.description AS des,level_id,skill.name AS skill_name,l.type AS level_type,ls.description, bs.schedule_id,bs.start_date,bs.end_date,\n" +
                    "b.status_id,s.type,acc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "FROM Booking b INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id  \n" +
                    "INNER JOIN Level_Skill ls ON ls.id = b.level_skill_id\n" +
                    "INNER JOIN Level l ON l.id = ls.level_id\n" +
                    "INNER JOIN Skill skill ON skill.id=ls.skill_id\n" +
                    "INNER JOIN Status s ON s.id = b.status_id\n" +
                    "INNER JOIN Mentee m ON m.account_id = b.mentee_id\n" +
                    "INNER JOIN Account acc ON acc.id = m.account_id\n" +
                    "WHERE b.mentor_id = ? \n" +
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
                skill.setName(resultSet.getString("skill_name"));

                Level lvl = new Level();
                lvl.setName(resultSet.getString("level_type"));

                Level_Skills level_skills = new Level_Skills();
                level_skills.setSkill(skill);
                level_skills.setLevel(lvl);
                level_skills.setDescription(resultSet.getString("description"));

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
                booking.setDescription(resultSet.getString("des"));

                BookingSchedule bs = new BookingSchedule();
                bs.setAccount(acc);
                bs.setSkill(level_skills);
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

    public BookingSchedule getBookNScheID(Timestamp start, Timestamp end, String id, String menteeID) {
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
            String sql = "SELECT b.id,b.mentee_id,create_date,amount,from_date,to_date,b.description AS des,level_id,skill.name AS skill_name,l.type AS level_type,ls.description, bs.schedule_id,bs.start_date,bs.end_date,\n" +
                    "b.status_id,s.type,acc.name,acc.address,acc.dob,acc.gender,acc.mail,acc.phone\n" +
                    "FROM Booking b INNER JOIN Booking_Schedule bs ON b.id = bs.booking_id  \n" +
                    "INNER JOIN Level_Skill ls ON ls.id = b.level_skill_id\n" +
                    "INNER JOIN Level l ON l.id = ls.level_id\n" +
                    "INNER JOIN Skill skill ON skill.id=ls.skill_id\n" +
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
                skill.setName(resultSet.getString("skill_name"));

                Level lvl = new Level();
                lvl.setName(resultSet.getString("level_type"));

                Level_Skills level_skills = new Level_Skills();
                level_skills.setSkill(skill);
                level_skills.setLevel(lvl);
                level_skills.setDescription(resultSet.getString("description"));

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
                booking.setDescription(resultSet.getString("des"));

                BookingSchedule bs = new BookingSchedule();
                bs.setAccount(acc);
                bs.setSkill(level_skills);
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

    public void updateImage(Account account) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Account]\n" +
                    "   SET [avatar] = ? \n" +
                    " WHERE Account.id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2, account.getId());
            preparedStatement.setString(1, account.getAvatar());
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Mentor> getAllMentorFollowRate() {
        ArrayList<Mentor> mentors = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Account.name, Account.id, Mentor.rating\n" +
                    "from Mentor join Account on Mentor.account_id = Account.id\n" +
                    "order by Mentor.rating desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor.setRating(resultSet.getFloat("rating"));
                mentors.add(mentor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mentors;
    }
    public void updateMentor(Mentor mentor, String id) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Mentor]\n" +
                    "   SET [profile_detail] = ?\n" +
                    "      ,[price] = ?\n" +
                    "      ,[experience] = ?\n" +
                    "      ,[education] = ?\n" +
                    " WHERE Mentor.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(5, id);
            preparedStatement.setString(1, mentor.getProfileDetail());
            preparedStatement.setInt(2, mentor.getPrice());
            preparedStatement.setString(3, mentor.getExperience());
            preparedStatement.setString(4, mentor.getEducation());
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
