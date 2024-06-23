package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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


    public ArrayList<Report> getAllProcessing(int page,String menteeName, String mentorName, String from, String to, String status_raw) {
        ArrayList<Report> list = new ArrayList<>();
        try {
            String sql = "SELECT r.id, r.reason,r.date,r.status_id,s.type ,accme.id as mentee_id, accme.name as mentee_name,\n" +
                    "\t\t accmo.id as mentor_id, accmo.name as mentor_name\t  \n" +
                    "  FROM [Report] r Join Mentee me ON r.mentee_id = me.account_id\n" +
                    "\t\tJOIN Account accme ON me.account_id = accme.id\n" +
                    "\t\tJOIN Mentor mo ON r.mentor_id = mo.account_id\n" +
                    "\t\tJOIN Account accmo ON mo.account_id = accmo.id\n" +
                    "\t\tJOIN Status s ON r.status_id = s.id\n"+
                    "Where 1=1";
//                    "  Where status_id = ?\n" +
            if(!menteeName.equals("")){
                sql += "   accme.name like '%"+menteeName+"%'\n";
            }
            if(!mentorName.equals("")){
                sql += "  and accmo.name like '%"+mentorName+"%'\n";
            }
            if(!from.equals("")){
                sql += "  and r.date >= '"+ from +"'\n";
            }
            if(!to.equals("")){
                sql += "  and r.date <= '"+ to +"'\n";
            }
            if(!status_raw.equals("")){
                if(!status_raw.equals("0"))  sql += "  and r.status_id = "+ status_raw +"\n";
            }


            sql +="  Order by date desc \n" +
                 "  OFFSET ? ROWS FETCH NEXT 4 ROWS ONLY";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, StatusEnum.PROCESSING);
            preparedStatement.setInt(1, (page - 1) * 4);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getInt("id"));
                report.setReason(rs.getString("reason"));
                report.setDate(rs.getTimestamp("date"));

                Status status = new Status();
                status.setId(rs.getInt("status_id"));
                status.setType(rs.getString("type"));
                report.setStatus(status);

                Account account = new Account();

                account.setId(rs.getString("mentee_id"));
                account.setName(rs.getString("mentee_name"));
                Mentee mentee = new Mentee();
                mentee.setAccount(account);
                report.setMentee(mentee);

                Account account1 = new Account();
                account1.setId(rs.getString("mentor_id"));
                account1.setName(rs.getString("mentor_name"));
                Mentor mentor = new Mentor();
                mentor.setAccount(account1);
                report.setMentor(mentor);

                list.add(report);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public int total(String menteeName, String mentorName, String from, String to, String status_raw) {
        try {
            String sql = "  SELECT Count(r.id) as total\n" +
                    "  FROM [Report] r Join Mentee me ON r.mentee_id = me.account_id\n" +
                    "\t\tJOIN Account accme ON me.account_id = accme.id\n" +
                    "\t\tJOIN Mentor mo ON r.mentor_id = mo.account_id\n" +
                    "\t\tJOIN Account accmo ON mo.account_id = accmo.id\n" +
                    "Where  1=1 \n";


            if(!menteeName.equals("")){
                sql += "   accme.name like '%"+menteeName+"%'\n";
            }
            if(!mentorName.equals("")){
                sql += "  and accmo.name like '%"+mentorName+"%'\n";
            }
            if(!from.equals("")){
                sql += "  and r.date >= "+ from +"\n";
            }
            if(!to.equals("")){
                sql += "  and r.date <= "+ to +"\n";
            }
            if(!status_raw.equals("")){
                if(!status_raw.equals("0"))  sql += "  and r.status_id = "+ status_raw +"\n";
            }

            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Report getReportAndMentorById(int id) {
        try {
            String sql = "  Select r.id, reason, date, status_id,mentee_id, mentor_id, name, a.username \n" +
                    "  From Report  r JOIN Mentor m ON r.mentor_id  = m.account_id\n" +
                    "\t\tJOIN Account a  ON m.account_id = a.id\n" +
                    "  Where r.id = ?";

            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getInt("id"));
                report.setReason(rs.getString("reason"));
                report.setDate(rs.getTimestamp("date"));

                Account account = new Account();
                account.setId(rs.getString("mentee_id"));
                ;
                Mentee mentee = new Mentee();
                mentee.setAccount(account);
                report.setMentee(mentee);

                Account account1 = new Account();
                account1.setId(rs.getString("mentor_id"));
                account1.setUserName(rs.getString("username"));
                account1.setName(rs.getString("name"));
                Mentor mentor = new Mentor();
                mentor.setAccount(account1);
                report.setMentor(mentor);
                return report;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateReport(Report report) {
        try {
            String sql = "UPDATE [dbo].[Report]\n" +
                    "   SET [status_id] = ?\n" +
                    "      ,[admin_id] = ?\n" +
                    " WHERE id = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.DONE);
            preparedStatement.setString(2, report.getAdministrator().getAccount().getId());
            preparedStatement.setInt(3, report.getId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
