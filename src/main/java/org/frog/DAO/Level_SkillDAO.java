package org.frog.DAO;

import org.frog.model.Category;
import org.frog.model.Level;
import org.frog.model.Level_Skills;
import org.frog.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Level_SkillDAO {


    public ArrayList<Level_Skills> getLevel_SkillList() {
        ArrayList<Level_Skills> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select ls.id, ls.description, ls.skill_id, s.name as skill, s.src_icon,\n" +
                    "\t\ts.cate_id,sc.name as category, ls.level_id\n" +
                    "From Level_Skill ls Join Skill s On ls.skill_id = s.id\n" +
                    "\t\tJoin Skill_Category sc On s.cate_id = sc.id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                level_skills.setId(resultSet.getInt("id"));
                level_skills.setDescription(resultSet.getString("description"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("skill"));
                skill.setSrc_icon(resultSet.getString("src_icon"));

                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));
                category.setName(resultSet.getString("category"));
                skill.setCategory(category);
                level_skills.setSkill(skill);

                Level level = new Level();
                level.setId(resultSet.getInt("level_id"));
                level_skills.setLevel(level);
                list.add(level_skills);
            }
            JDBC.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Level_Skills> getLevel_SkillByMentorId(String id) {
        ArrayList<Level_Skills> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Skill.id as skill_id, Skill.name, Skill.src_icon, Level.id, Level.type\n" +
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
                Level_Skills level_skills = new Level_Skills();

                Level level = new Level();
                level.setId(resultSet.getInt("id"));
                level.setName(resultSet.getString("type"));
                level_skills.setLevel(level);

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));
                level_skills.setSkill(skill);

                list.add(level_skills);
            }
            JDBC.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Level_Skills> getLevel_Skill(String name, String[] levels) {
        ArrayList<Level_Skills> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC.getConnection();
            String sql = "SELECT Skill.id, Skill.name, Skill.src_icon, Level_Skill.id as level_skill_id, Level_Skill.level_id, Level.type, " +
                    "Level_Skill.description, Skill.cate_id, Skill_Category.name as category " +
                    "FROM [Level] " +
                    "INNER JOIN Level_Skill ON [Level].id = Level_Skill.level_id " +
                    "INNER JOIN Skill ON Level_Skill.skill_id = Skill.id " +
                    "INNER JOIN Skill_Category ON Skill.cate_id = Skill_Category.id " +
                    "WHERE Skill.name like '%" + name + "%'";

            if (levels != null && levels.length > 0) {
                sql += " AND (";
                for (int i = 0; i < levels.length; i++) {
                    sql += " Level.type = ?";
                    if (i < levels.length - 1) {
                        sql += " OR";
                    }
                }
                sql += " )";
            }

            preparedStatement = connection.prepareStatement(sql.toString());
//            preparedStatement.setString(1, name);

            if (levels != null && levels.length > 0) {
                for (int i = 0; i < levels.length; i++) {
                    preparedStatement.setString(1 + i, levels[i]);
                }
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                level_skills.setId(resultSet.getInt("level_skill_id"));
                level_skills.setDescription(resultSet.getString("description"));

                Level level = new Level();
                level.setId(resultSet.getInt("level_id"));
                level.setName(resultSet.getString("type"));
                level_skills.setLevel(level);

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));
                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));
                category.setName(resultSet.getString("category"));
                skill.setCategory(category);
                level_skills.setSkill(skill);

                list.add(level_skills);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
