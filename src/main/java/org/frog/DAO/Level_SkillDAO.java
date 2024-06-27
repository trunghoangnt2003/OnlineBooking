package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public ArrayList<Level_Skills> getAllLevel_SkillList() {
        ArrayList<Level_Skills> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Level_Skill.id, Level.type, Skill.name,Skill.cate_id\n" +
                    "from Level join Level_Skill on Level.id = Level_Skill.level_id\n" +
                    "\t\tjoin Skill on Skill.id = Level_Skill.skill_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                Skill skill = new Skill();
                Level level = new Level();

                level_skills.setId(resultSet.getInt("id"));
                level_skills.setLevel(level);
                skill.setName(resultSet.getString("name"));
                Category category = new Category();
                category.setId(resultSet.getInt("cate_id"));
                skill.setCategory(category);
                level.setName(resultSet.getString("type"));
                level_skills.setSkill(skill);
                level_skills.setLevel(level);

                list.add(level_skills);
            }
            JDBC.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Level_Skills> getLevel_SkillListPagitaion(int page,int cate) {
        ArrayList<Level_Skills> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "Select ls.id, ls.description, ls.skill_id, s.name as skill, s.src_icon,\n" +
                    "                  s.cate_id,sc.name as category, ls.level_id,l.type,ls.status_id\n" +
                    "                    From Level_Skill ls Join Skill s On ls.skill_id = s.id\n" +
                    "                    Join Skill_Category sc On s.cate_id = sc.id\n" +
                    "join Level l on l.id=ls.level_id\n";
                    if(cate != 0 ){
                        sql += "where sc.id = ?\n";
                    }
                    sql +=   "order by ls.id\n";
                    sql +=       "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if(cate != 0 ){
                preparedStatement.setInt(1, cate);
                preparedStatement.setInt(2, (page - 1) * 10);
            }else {
                preparedStatement.setInt(1, (page - 1) * 10);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                level_skills.setId(resultSet.getInt("id"));
                level_skills.setDescription(resultSet.getString("description"));
                level_skills.setStatus(resultSet.getInt("status_id"));
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
                level.setName(resultSet.getString("type"));
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
            String sql = "SELECT Skill.id, Skill.name, Skill.src_icon, Level_Skill.id as level_skill_id, Level_Skill.level_id,Level_Skill.status_id ,Level.type, " +
                    "Level_Skill.description, Skill.cate_id, Skill_Category.name as category " +
                    "FROM [Level] " +
                    "INNER JOIN Level_Skill ON [Level].id = Level_Skill.level_id " +
                    "INNER JOIN Skill ON Level_Skill.skill_id = Skill.id " +
                    "INNER JOIN Skill_Category ON Skill.cate_id = Skill_Category.id " +
                    "WHERE Skill.name like '%" + name + "%' AND Level_Skill.status_id = ? ";

            if (levels != null && levels.length > 0) {
                sql += " AND (";
                for (int i = 0; i < levels.length; i++) {
                    sql += " Level.type = '" + levels[i]+ "' ";
                    if (i < levels.length - 1) {
                        sql += " OR";
                    }
                }
                sql += " )";
            }

            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.ACTIVE);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                level_skills.setId(resultSet.getInt("level_skill_id"));
                level_skills.setDescription(resultSet.getString("description"));
                level_skills.setStatus(resultSet.getInt("status_id"));

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

    public Level_Skills getByMentorId(String id) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Skill.id as skill_id, Skill.name, Level.id, Level.type\n" +
                    "FROM     [Level] INNER JOIN\n" +
                    "                  Level_Skill ON [Level].id = Level_Skill.level_id INNER JOIN\n" +
                    "                  Mentor_Level_Skill ON Level_Skill.id = Mentor_Level_Skill.skill_level_id INNER JOIN\n" +
                    "                  Mentor ON Mentor_Level_Skill.mentor_id = Mentor.account_id INNER JOIN\n" +
                    "                  Skill ON Level_Skill.skill_id = Skill.id\n" +
                    "\t\t\t\t  where Mentor.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();

                Level level = new Level();
                level.setId(resultSet.getInt("id"));
                level.setName(resultSet.getString("type"));
                level_skills.setLevel(level);

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("name"));
                level_skills.setSkill(skill);

               return  level_skills;
            }
            JDBC.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertLevelSkill(String accountId, int lsId) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Mentor_Level_Skill]\n" +
                    "           ([mentor_id]\n" +
                    "           ,[skill_level_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(2, lsId);
            preparedStatement.setString(1, accountId);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Level_Skills getBySkillAndLevel(String skill_name, String level_name) {
        try{
            Connection connection = JDBC.getConnection();
            String sql = "Select ls.id, ls.skill_id, s.name, s.src_icon, sc.name as category, \n" +
                    "\t\tls.level_id, l.type\n" +
                    "From Level_Skill ls JOIN [Level] l ON ls.level_id = l.id\n" +
                    "\t\tJOIN Skill s ON ls.skill_id = s.id \n" +
                    "\t\tJOIN Skill_Category sc ON s.cate_id = sc.id\n" +
                    "WHERE s.name = ? AND l.type = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, skill_name);
            preparedStatement.setString(2, level_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Level_Skills level_skills = new Level_Skills();
                level_skills.setId(resultSet.getInt("id"));

                Skill skill = new Skill();
                skill.setId(resultSet.getInt("skill_id"));
                skill.setName(resultSet.getString("name"));
                skill.setSrc_icon(resultSet.getString("src_icon"));

                Category category = new Category();
                category.setName(resultSet.getString("category"));
                skill.setCategory(category);
                level_skills.setSkill(skill);

                Level level = new Level();
                level.setId(resultSet.getInt("level_id"));
                level.setName(resultSet.getString("type"));
                level_skills.setLevel(level);
                return level_skills;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void updateMentorSkill(String accountId, int lsId) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Mentor_Level_Skill]\n" +
                    "   SET [skill_level_id] = ?\n" +
                    " WHERE mentor_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lsId);
            preparedStatement.setString(2, accountId);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateStatus( int id, int status) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Level_Skill]\n" +
                    "   SET\n" +
                    "      [status_id] = ?\n" +
                    " WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addNewLevelSkill(int skill_id, int level_id,String description) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Level_Skill]\n" +
                    "           ([skill_id]\n" +
                    "           ,[level_id]\n" +
                    "           ,[description]\n" +
                    "           ,[status_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,7)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,skill_id);
            preparedStatement.setInt(2,level_id);
            preparedStatement.setString(3,description);
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
