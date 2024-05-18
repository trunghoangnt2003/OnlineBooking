package org.frog.DAO;

import org.frog.model.Account;
import org.frog.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenterDAO {

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
                mentor.setSkills(  skillsDAO.getByMentorId(account.getId()));
                list.add(mentor); 
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {

        MenterDAO menterDAO = new MenterDAO();
        ArrayList<Mentor> list = menterDAO.selectAll();
        for (Mentor mentor : list) {
            System.out.println(mentor.getAccount().getId());
        }
    }
}
