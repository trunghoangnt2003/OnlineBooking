package org.frog.DAO;

import org.frog.model.*;

import java.sql.*;
import java.util.*;

public class MenteeDAO {
    public Map<Mentee, Map<String, Integer>> getStaticAllMentee(String nameSearch) {
        Map<Mentee, Map<String, Integer>> menteeMap = new LinkedHashMap<>();

        String sql = "SELECT \n" +
                "    a.id, \n" +
                "    a.name, \n" +
                "    a.username, \n" +
                "    a.status, \n" +
                "    COUNT(DISTINCT b.level_skill_id) AS TotalSkill, \n" +
                "    COUNT(DISTINCT Schedule.slot_id) AS TotalSlots\n" +
                "FROM \n" +
                "    Account a \n" +
                "JOIN \n" +
                "    Mentee me ON a.id = me.account_id\n" +
                "LEFT JOIN \n" +
                "    Booking b ON me.account_id = b.mentee_id AND (b.status_id = 3 OR b.status_id = 11)\n" +
                "LEFT JOIN \n" +
                "    Status ON Status.id = b.status_id\n" +
                "LEFT JOIN \n" +
                "    Booking_Schedule ON Booking_Schedule.booking_id = b.id\n" +
                "LEFT JOIN \n" +
                "    Schedule ON Schedule.id = Booking_Schedule.schedule_id\n" +
                "WHERE \n" +
                "    a.name LIKE ?\n" +
                "GROUP BY \n" +
                "    a.id, a.name, a.username,a.status\n" +
                "ORDER BY \n" +
                "    a.name;";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Ensure the nameSearch parameter is handled correctly
            if (nameSearch == null || nameSearch.isEmpty()) {
                nameSearch = ""; // If empty or null, search for all names
            }

            preparedStatement.setString(1, "%" + nameSearch + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getString("id"));
                    account.setName(resultSet.getString("name"));
                    account.setUserName(resultSet.getString("username"));
                    account.setStatus(new Status(resultSet.getInt("status"),""));
                    Mentee mentee = new Mentee();
                    mentee.setAccount(account);

                    Map<String, Integer> total = new HashMap<>();
                    total.put("TotalSkill", resultSet.getInt("TotalSkill"));
                    total.put("TotalSlots", resultSet.getInt("TotalSlots"));

                    menteeMap.put(mentee, total);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // or use a logger to log the exception
        }

        return menteeMap;
    }



    public Mentee getMenteeById(String id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC.getConnection();
            String sql = "SELECT * FROM Mentee " +
                    "JOIN Account ON Account.id = Mentee.account_id " +
                    "WHERE Mentee.account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setDob(resultSet.getDate("dob"));
                account.setPhone(resultSet.getString("phone"));
                account.setGender(resultSet.getInt("gender"));
                account.setAddress(resultSet.getString("address"));
                account.setUserName(resultSet.getString("username"));
                account.setEmail(resultSet.getString("mail"));
                account.setAvatar(resultSet.getString("avatar"));

                Mentee mentee = new Mentee(account);
                return mentee;
            } else {

            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the stack trace
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();  // Log the stack trace for resource cleanup issues
            }
        }
        return null;
    }



    public void updateMentee(Mentee mentee) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Account]\n" +
                    "   SET \n" +
                    "     [username] = ?\n" +
                    "      ,[name] = ?\n" +
                    "      ,[dob] = ?\n" +
                    "      ,[phone] = ?\n" +
                    "      ,[gender] = ?\n" +
                    "      ,[address] = ?\n" +
                    "      ,[avatar] = ?\n" +
                    " WHERE Account.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentee.getAccount().getUserName());
            preparedStatement.setString(2, mentee.getAccount().getName());
            preparedStatement.setDate(3, mentee.getAccount().getDob());
            preparedStatement.setString(4, mentee.getAccount().getPhone());
            preparedStatement.setInt(5, mentee.getAccount().getGender());

            preparedStatement.setString(6, mentee.getAccount().getAddress());
            preparedStatement.setString(7, mentee.getAccount().getAvatar());
            preparedStatement.setString(8, mentee.getAccount().getId());
            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();  // Log the stack trace
        }
    }
    public void insert(Account account) {
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Mentee]\n" +
                    "           ([account_id])\n" +
                    "     VALUES\n" +
                    "           (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getId());

            preparedStatement.executeUpdate();
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();  // Log the stack trace
        }
    }

    public ArrayList<Mentee> getListMenteeBookingMentor(String id) {
        ArrayList<Mentee> mentees = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT A.id, A.name, A.mail, A.address, MIN(B.create_date) as create_date, MIN(B.from_date) as from_date, MAX(B.to_date) as to_date, SUM(B.total_slot) as total_slot\n" +
                    "FROM Account A\n" +
                    "JOIN Mentee Me on A.id = Me.account_id\n" +
                    "JOIN Booking B on Me.account_id = B.mentee_id\n" +
                    "WHERE B.mentor_id = ?\n" +
                    "GROUP BY A.id, A.name, A.mail, A.address";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                Mentee mentee = new Mentee();
                Booking booking = new Booking();
                booking.setTotalSlot(resultSet.getInt("total_slot"));
                booking.setStartDate(resultSet.getDate("from_date"));
                booking.setEndDate(resultSet.getDate("to_date"));
                mentee.setBooking(booking);

                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("mail"));
                account.setAddress(resultSet.getString("address"));

                mentee.setAccount(account);

                mentees.add(mentee);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log the stack trace
        }
        return mentees;
    }

}



