package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentor;

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
                mentor.setPrice(resultSet.getFloat("price"));
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

    public ArrayList<Mentor> getMentorAndPaging(int page) {
        ArrayList<Mentor> list = new ArrayList<>();
        try {

            Connection connection = JDBC.getConnection();
            String sql = "Select a.id,a.username,a.name,a.dob,a.avatar,m.experience,m.price,m.rating\n" +
                    "From Account a JOIN Mentor m on a.id = m.account_id\n" +
                    "ORDER BY a.id\n" +
                    "OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (page - 1) * 6);
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
                mentor.setPrice(resultSet.getFloat("price"));
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

    public int totalMentor() {
        int total = 0;
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Count (Distinct a.id) As total_mentor\n" +
                    "from Account a JOIN Mentor m On a.id = m.account_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

}
