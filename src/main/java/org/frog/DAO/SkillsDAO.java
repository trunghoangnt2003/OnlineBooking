package org.frog.DAO;

import org.frog.model.Category;
import org.frog.model.Level;
import org.frog.model.Skill;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkillsDAO {


    public ArrayList<Skill> getAllActive() {
        ArrayList<Skill> list = new ArrayList<>();
        String sql = "SELECT DISTINCT Skill.id as skill_id, Skill.name, Skill.cate_id, Skill.src_icon " +
                "FROM Level_Skill " +
                "INNER JOIN Skill ON Level_Skill.skill_id = Skill.id " +
                "WHERE Level_Skill.status_id = ? " +
                "ORDER BY Skill.name ASC";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, StatusEnum.ACTIVE);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("skill_id");
                String name = resultSet.getString("name");
                String src_icon = resultSet.getString("src_icon");
                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));

                Skill skill = new Skill();
                skill.setId(id);
                skill.setName(name);
                skill.setSrc_icon(src_icon);
                skill.setCategory(category);
                list.add(skill);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }



    public Skill getByName(String skillName) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [Skill]\n" +
                    "where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, skillName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String src_icon = resultSet.getString("src_icon");
                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(name);
                skill.setSrc_icon(src_icon);
                skill.setCategory(category);
                return skill;
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Skill> getSkillByMentorId(String id) {
        ArrayList<Skill> skills = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Skill.id, Skill.name, Level.id, Level.type\n" +
                    "FROM     [Level] INNER JOIN\n" +
                    "                  Level_Skill ON [Level].id = Level_Skill.level_id INNER JOIN\n" +
                    "                  Mentor_Level_Skill ON Level_Skill.id = Mentor_Level_Skill.skill_level_id INNER JOIN\n" +
                    "                  Mentor ON Mentor_Level_Skill.mentor_id = Mentor.account_id INNER JOIN\n" +
                    "                  Skill ON Level_Skill.skill_id = Skill.id\n" +
                    "\t\t\t\t  where Mentor.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Level level = new Level();
                level.setId(resultSet.getInt("id"));
                level.setName(resultSet.getString("type"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));

                skills.add(skill);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }

    public ArrayList<Skill> getSkillByCateId(int id) {
        ArrayList<Skill> skills = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT *\n" +
                    "FROM     Skill INNER JOIN\n" +
                    "                  Skill_Category ON Skill.cate_id = Skill_Category.id\n" +
                    "\t\t\t\t  where cate_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                skills.add(skill);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return skills;
    }

}
