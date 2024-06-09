package org.frog.DAO;

import org.frog.model.Account;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeatureDAO {

    public boolean checkPublic(String path) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select f.*\n" +
                    "from Feature f left join Role_Feature rf on f.id = rf.feature_id\n" +
                    "where f.url = ? and rf.role_id = 5";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, path);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }


            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return false;
    }
    public boolean checkRole(String path,int role) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select f.*\n" +
                    "from Feature f left join Role_Feature rf on f.id = rf.feature_id\n" +
                    "where f.url = ? and rf.role_id = ?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, path);
            preparedStatement.setInt(2, role);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }


            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
        }
        return false;
    }
}
