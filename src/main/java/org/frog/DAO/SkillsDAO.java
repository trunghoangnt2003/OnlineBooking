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
            String sql = "select * from [Skill]\n" +
                    "order by name asc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
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
            JDBC.closeConnection(connection);
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

}
