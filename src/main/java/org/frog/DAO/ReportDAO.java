package org.frog.DAO;

import org.frog.model.Report;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReportDAO {

    public void insert(Report report) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Report]\n" +
                    "           ([reason]\n" +
                    "           ,[date]\n" +
                    "           ,[status_id]\n" +
                    "           ,[mentor_id]\n" +
                    "           ,[mentee_id] )\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,GETDATE()\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,? )";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, report.getReason());
            preparedStatement.setInt(2, StatusEnum.PROCESSING);
            preparedStatement.setString(3, report.getMentor().getAccount().getId());
            preparedStatement.setString(4, report.getMentee().getAccount().getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
