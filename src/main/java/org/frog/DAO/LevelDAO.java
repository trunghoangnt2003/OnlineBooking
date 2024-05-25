package org.frog.DAO;

import org.frog.model.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LevelDAO {

    public ArrayList<Level> getAll() {
        ArrayList<Level> list = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select * from [Level]";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("type");
                Level level = new Level();
                level.setId(id);
                level.setName(name);
                list.add(level);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
