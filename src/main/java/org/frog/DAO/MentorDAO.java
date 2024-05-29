package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentor;
import org.frog.utility.OrderEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                System.out.println(mentor.getTotalBookings());
                list.add(mentor); 
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Mentor> getMentorAndPaging(int page, String skill, String level,String search, int order ) {
        if (search == null){
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
                    "Left join Booking b ON a.id = b.mentor_id and status_id = 3\n" +
                    "Where s.name = ? And l.type = ? AND a.name like '%"+ search +"%' \n" +
                    "Group By a.id,a.username,a.name,a.dob,a.avatar,\n" +
                    "m.experience,m.price,m.rating, s.name, l.type\n" ;

            if (order == OrderEnum.POPULAR){
                sql += "ORDER BY  totalBooking DESC \n" ;
            }else if( order == OrderEnum.RATE){
                sql += "ORDER BY  rating DESC \n" ;
            }else{
                sql += "ORDER BY  id \n" ;
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
            String sql = "UPDATE [dbo].[Mentor]\n" +
                    "   SET [profile_detail] = ?\n" +
                    "      ,[price] = ?\n" +
                    "      ,[experience] = ?\n" +
                    "      ,[education] = ?\n" +
                    " WHERE Mentor.account_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentor.getProfileDetail());
            preparedStatement.setInt(2, mentor.getPrice());
            preparedStatement.setString(3, mentor.getExperience());
            preparedStatement.setString(4, mentor.getEducation());
            preparedStatement.setString(5, mentor.getAccount().getId());
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
//                System.out.println(mentor.getAccount().getId());
//                System.out.println(mentor.getAccount().getName());
//                System.out.println(mentor.getAccount().getDob());
//                System.out.println(mentor.getAccount().getPhone());
//                System.out.println(mentor.getAccount().getGender());
//                System.out.println(mentor.getAccount().getAddress());
//                System.out.println(mentor.getAccount().getEmail());
//                System.out.println(mentor.getAccount().getAvatar());
//                System.out.println(mentor.getProfileDetail());
//                System.out.println(mentor.getPrice());
//                System.out.println(mentor.getExperience());
//                System.out.println(mentor.getEducation());

                return mentor;
            }
            JDBC.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
