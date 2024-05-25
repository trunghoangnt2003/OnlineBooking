package org.frog.DAO;

import org.frog.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {

    public ArrayList<Category> getAll() {
        ArrayList<Category> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [Skill_Category]\n" +
                    "order by name asc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category();
                category.setId(id);
                category.setName(name);
                list.add(category);
            }
            JDBC.closeConnection(connection);
            return list;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


}
