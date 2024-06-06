package org.frog.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDAO {
    public void updateStatusByAdmin(String idAccount,String idAdmin,int status) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Account_Status_Log]\n" +
                    "           ([acount_id]\n" +
                    "           ,[admin_id]\n" +
                    "           ,[date]\n" +
                    "           ,[status_id])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,GETDATE()\n" +
                    "           ,?)\n";
            PreparedStatement preparedStatement = null;
            if (connection != null) {

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, idAccount);
                preparedStatement.setString(2, idAdmin);
                preparedStatement.setInt(3, status);
                preparedStatement.executeUpdate();

            }

            JDBC.closeConnection(connection);
        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
    }
}
