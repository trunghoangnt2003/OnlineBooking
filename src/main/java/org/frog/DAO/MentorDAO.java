package org.frog.DAO;

import org.frog.model.*;
import org.frog.model.Account;
import org.frog.model.Level_Skills;
import org.frog.model.Mentor;
import org.frog.utility.OrderEnum;
import org.frog.utility.StatusEnum;
import org.frog.model.Status;
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Mentor> getMentorAndPaging(int page, String skill, String level, String search, int order) {
        if (search == null) {
            search = "";
        }
        ArrayList<Mentor> list = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "Select a.id,a.username,a.name as mentor_name,a.dob,a.avatar,\n" +
                    "m.experience,m.price,m.rating, s.name as skill, l.type, Count(b.mentor_id) as totalBooking\n" +
                    "From Account a JOIN Mentor m on a.id = m.account_id\n" +
                    "JOIN Mentor_Level_Skill mls ON m.account_id = mls.mentor_id\n" +
                    "JOin Level_Skill ls on mls.skill_level_id = ls.id\n" +
                    "JOin Skill  s ON ls.skill_id = s.id\n" +
                    "Join Level l On ls.level_id = l.id\n" +
                    "Left join Booking b ON a.id = b.mentor_id and b.status_id = 3\n" +
                    "Where s.name = ? And l.type = ? AND a.name like '%" + search + "%' \n" +
                    "Group By a.id,a.username,a.name,a.dob,a.avatar,\n" +
                    "m.experience,m.price,m.rating, s.name, l.type\n";

            if (order == OrderEnum.POPULAR) {
                sql += "ORDER BY  totalBooking DESC \n";
            } else if (order == OrderEnum.RATE) {
                sql += "ORDER BY  rating DESC \n";
            } else {
                sql += "ORDER BY  id \n";
            }
            sql += "OFFSET ? ROWS FETCH NEXT 4 ROWS ONLY";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, skill);
            preparedStatement.setString(2, level);
            preparedStatement.setInt(3, (page - 1) * 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("mentor_name"));
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
                mentor.setTotalBookings(resultSet.getInt("totalBooking"));

                list.add(mentor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public ArrayList<Mentor> getMentorAndPagingAndSearch(int page, String name) {
        ArrayList<Mentor> list = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "with t as (\n" +
                    "\tselect m.account_id,count(b.id) as [bookingTotal]\n" +
                    "\tfrom Mentor m join Booking b on m.account_id=b.mentor_id\n" +
                    "\twhere b.status_id = 11 or b.status_id = 3\n" +
                    "\tgroup by m.account_id\n" +
                    "),t1 as (\n" +
                    "\tselect m.account_id,count(b.id) as [bookingDone]\n" +
                    "\tfrom Mentor m join Booking b on m.account_id=b.mentor_id\n" +
                    "\twhere b.status_id = 3 \n" +
                    "\tgroup by m.account_id\n" +
                    ")\n" +
                    "\n" +
                    "select a.id,a.name,a.username,m.rating,a.status,ISNULL(t.bookingTotal,0) as totalBooking , IsNull((1.0*t1.bookingDone/t.bookingTotal)*100,0) as percentageCompleted\n" +
                    "from Account a join Mentor m on a.id=m.account_id\n" +
                    "full join t on t.account_id=m.account_id\n" +
                    "full join t1 on t1.account_id = m.account_id\n" +
                    "where a.username like '%"+name+"%'\n" +
                    "order by a.id\n" +
                    "\n" +
                    "OFFSET ? ROWS FETCH NEXT 4 ROWS ONLY";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (page - 1) * 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setUserName(resultSet.getString("username"));
                account.setStatus(new Status(resultSet.getInt("status"),""));
                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor.setRating(resultSet.getFloat("rating"));


                mentor.setTotalBookings(resultSet.getInt("totalBooking"));
                mentor.setPercentageCompleted(resultSet.getInt("percentageCompleted"));

                Level_SkillDAO levelSkillDAO = new Level_SkillDAO();
                ArrayList<Level_Skills> skills = levelSkillDAO.getLevel_SkillByMentorId(account.getId());
                mentor.setLevel_skills(skills);
                list.add(mentor);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
    public int totalMentorAndPagingAndSearch(int page, String name) {
        int totalMentor = 0;
        try {

            Connection connection = JDBC.getConnection();
            String sql = "with t as (\n" +
                    "\tselect m.account_id,count(b.id) as [bookingTotal]\n" +
                    "\tfrom Mentor m join Booking b on m.account_id=b.mentor_id\n" +
                    "\twhere b.status_id = 11 or b.status_id = 3\n" +
                    "\tgroup by m.account_id\n" +
                    "),t1 as (\n" +
                    "\tselect m.account_id,count(b.id) as [bookingDone]\n" +
                    "\tfrom Mentor m join Booking b on m.account_id=b.mentor_id\n" +
                    "\twhere b.status_id = 3 \n" +
                    "\tgroup by m.account_id\n" +
                    ")\n" +
                    "\n" +
                    "select a.id,a.name,a.username,m.rating,a.status,ISNULL(t.bookingTotal,0) as totalBooking , IsNull((1.0*t1.bookingDone/t.bookingTotal)*100,0) as percentageCompleted\n" +
                    "from Account a join Mentor m on a.id=m.account_id\n" +
                    "full join t on t.account_id=m.account_id\n" +
                    "full join t1 on t1.account_id = m.account_id\n" +
                    "where a.username like '%"+name+"%'\n" +
                    "order by a.id\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalMentor++;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return totalMentor;
    }


    public int totalMentor(String skill, String level, String search) {
        int total = 0;
        if (search == null) {
            search = "";
        }
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select Count(m.account_id)\n" +
                    "From Account a JOIN Mentor m on a.id = m.account_id\n" +
                    "\t JOIN Mentor_Level_Skill mls ON m.account_id = mls.mentor_id\n" +
                    "\t JOin Level_Skill ls on mls.skill_level_id = ls.id\n" +
                    "\t JOin Skill  s ON ls.skill_id = s.id\n" +
                    "\t Join Level l On ls.level_id = l.id\n" +
                    "Where s.name = ? And l.type = ? AND a.name like '%" + search + "%'\n";
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
            String sql = "UPDATE [dbo].[Mentor]\n" +
                    "   SET [profile_detail] = ?\n" +
                    "      ,[price] = ?\n" +
                    "      ,[experience] = ?\n" +
                    "      ,[education] = ?\n" +
                    "      \n" +
                    " WHERE Mentor.account_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(5, mentor.getAccount().getId());
            preparedStatement.setString(1, mentor.getProfileDetail());
            preparedStatement.setInt(2, mentor.getPrice());
            preparedStatement.setString(3, mentor.getExperience());
            preparedStatement.setString(4, mentor.getEducation());
            preparedStatement.execute();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void register(Account account) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Mentor]\n" +
                    "           ([account_id])\n" +
                    "\n" +
                    "     VALUES\n" +
                    "           (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getId());

            preparedStatement.executeUpdate();
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

    public ArrayList<Mentor> getByBookDone(String mentee_id) {
        ArrayList<Mentor> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select a.id, a.name, m.price,a.mail\n" +
                    "From Mentor m JOIN Account a ON m.account_id = a.id\n" +
                    "Where m.account_id in (SELEct Distinct b.mentor_id \n" +
                    "From Booking b\n" +
                    "Where b.status_id = ? \n" +
                    "and b.mentee_id = ? \n" +
                    ")";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.DONE);
            preparedStatement.setString(2, mentee_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Mentor mentor = new Mentor();
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("mail"));
                mentor.setAccount(account);
                mentor.setPrice(resultSet.getInt("price"));

                list.add(mentor);
            }
            JDBC.closeConnection(connection);

        } catch (Exception e) {
                e.printStackTrace();
        }
        return list;
    }

    public Mentor getById(String id){
        try{
         String sql = "Select account_id, profile_detail, price, experience, education, rating\n" +
                 "From Mentor\n" +
                 "Where account_id = ?"   ;
         Connection connection = JDBC.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setString(1, id);
         ResultSet resultSet = preparedStatement.executeQuery();
         if(resultSet.next()){
             Mentor mentor = new Mentor();

             Account account = new Account();
             account.setId(resultSet.getString("account_id"));
             mentor.setAccount(account);
             mentor.setProfileDetail(resultSet.getString("profile_detail"));
             mentor.setPrice(resultSet.getInt("price"));
             mentor.setExperience(resultSet.getString("experience"));
             mentor.setEducation(resultSet.getString("education"));
             mentor.setRating(resultSet.getFloat("rating"));
             return mentor;
         }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void updateRatingById(String id,float  rating){
        try{
            String sql = "UPDATE [dbo].[Mentor]\n" +
                    "   SET [rating] = ?\n" +
                    " WHERE account_id = ? "   ;
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, rating);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


//    public ArrayList<Schedule> getScheduleByMentorID(String id) {
//        ArrayList<Schedule> schedules = new ArrayList<>();
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = JDBC.getConnection();
//            String sql = "SELECT \n" +
//                    "\tsche.id,\n" +
//                    "      [start_date]\n" +
//                    "      ,[end_date]\n" +
//                    "      ,[status]\n" +
//                    "      ,[account_id]\n" +
//                    "  FROM [Schedule] sche\n" +
//                    "  Where account_id = ?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, id);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Schedule schedule = new Schedule();
//                schedule.setId(resultSet.getInt("id"));
//                schedule.setDateStart(resultSet.getTimestamp("start_date"));
//                schedule.setDateEnd(resultSet.getTimestamp("end_date"));
//                schedule.setStatus(resultSet.getBoolean("status"));
//
//                schedules.add(schedule);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) resultSet.close();
//                if (preparedStatement != null) preparedStatement.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return schedules;
//    }

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
            String sql = "select Account.name, Account.id, Account.avatar, Mentor.rating\n" +
                    "from Mentor join Account on Mentor.account_id = Account.id\n" +
                    "order by Mentor.rating desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setAvatar(resultSet.getString("avatar"));
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

    public ArrayList<Level_Skills> getSkillMentor(String accountId) {
        ArrayList<Level_Skills> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Mentor_Level_Skill.skill_level_id from Mentor_Level_Skill\n" +
                    "where Mentor_Level_Skill.mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                level_skills.setId(resultSet.getInt("skill_level_id"));
                list.add(level_skills);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void deleteSkillMentor(String accountId) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "DELETE FROM [dbo].[Mentor_Level_Skill]\n" +
                    "      WHERE mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountId);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
