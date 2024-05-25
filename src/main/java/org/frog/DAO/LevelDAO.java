package org.frog.DAO;

import org.frog.model.Level;

import java.sql.*;
import java.util.ArrayList;

public class LevelDAO {
    public ArrayList<Level> getAll() {
        ArrayList<Level> levels = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [Level]";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Level level = new Level();
                level.setId(resultSet.getInt("id"));
                level.setName(resultSet.getString("name"));
                levels.add(level);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return levels;
    }
}
