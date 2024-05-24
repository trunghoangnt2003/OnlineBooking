package org.frog.DAO;

import org.frog.model.Category;
import org.frog.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkillsDAO {


    public ArrayList<Skill >getAll() {
        ArrayList<Skill> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * \n" +
                    "from [Skill] \n" +
                    "order by name asc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));
                Skill skill = new Skill(id, name, category, null);
                list.add(skill);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Skill> getByMentorId(String mentor_id) {
        ArrayList<Skill> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select Skill.id, name, cate_id\n" +
                    "from Mentor_Skill join Skill on Mentor_Skill.skill_id = Skill.id\n" +
                    "Where mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentor_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));

                Skill skill = new Skill(id, name, category, null);
                list.add(skill);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;

    }


    public static void main(String[] args) {

        SkillsDAO skillsDAO = new SkillsDAO();
        ArrayList<Skill> list = skillsDAO.getByMentorId("1525cfd4-fbb9-4667-9d00-2a54582a2f28");
        for (Skill skill : list) {
            System.out.println(skill);
        }
    }
}
