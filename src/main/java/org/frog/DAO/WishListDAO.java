package org.frog.DAO;

import org.frog.model.*;
import org.frog.utility.StatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishListDAO {
    public ArrayList<WishList> getWishListByMentorId(String id) {
        ArrayList<WishList> wishLists = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT Account.id, Account.name, Wish_List.status_id, Wish_List.date\n" +
                    "FROM    Wish_List join Mentee on Wish_List.mentee_id = Mentee.account_id\n" +
                    "\t\t\tjoin Account on Mentee.account_id = Account.id\n" +
                    "\t\t\t\t  where Wish_List.mentor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WishList wishList = new WishList();
                Mentee mentee = new Mentee();
                Account account = new Account();
                account.setId(resultSet.getString("id"));
                account.setName(resultSet.getString("name"));

                mentee.setAccount(account);
                wishList.setMentee(mentee);
                wishList.setTimeRequest(resultSet.getDate("date"));
                wishLists.add(wishList);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return wishLists;
    }

    public List<WishList> getWishListProcessByMenteeId(String id) {
        List<WishList> wishLists = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Wish_List.id,Account.id as ac_id, Account.name,Account.mail, Mentor.experience, Account.avatar, status.type, Wish_List.date from Account\n" +
                    "                                                           join Mentor \n" +
                    "                                                            on Account.id = Mentor.account_id\n" +
                    "                                                            join Wish_List\n" +
                    "                                                            on Mentor.account_id = Wish_List.mentor_id\n" +
                    "                                                           join Status\n" +
                    "                                                         on Status.id = Wish_List.status_id\n" +
                    "                                                           where Wish_List.mentee_id = ? and Wish_List.status_id = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("ac_id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("mail"));
                account.setAvatar(resultSet.getString("avatar"));

                Mentor mentor = new Mentor();
                mentor.setAccount(account);
                mentor.setExperience(resultSet.getString("experience"));


                // Ensure Account ID is being set correctly
                if (mentor.getAccount().getId() != null) {
                    Level_SkillDAO level_skillDAO = new Level_SkillDAO();
                    ArrayList<Level_Skills> level_skills = level_skillDAO.getLevel_SkillByMentorId(mentor.getAccount().getId());
                    mentor.setLevel_skills(level_skills);
                } else {
                    System.out.println("Account ID is null for mentor");
                }
                Status status = new Status();
                status.setType(resultSet.getString("type"));
                WishList wishList = new WishList();
                wishList.setMentor(mentor);
                wishList.setStatus(status);
                wishList.setId(resultSet.getInt("id"));
                wishList.setTimeRequest(resultSet.getDate("date"));
                wishLists.add(wishList);
            }
            JDBC.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return wishLists;
    }


    public List<WishList> getWishListAcceptByMenteeId(String id) {
        List<WishList> wishLists = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "select Wish_List.id,Account.id as ac_id, Account.name,Account.mail, Mentor.experience, Account.avatar, status.type, Wish_List.date from Account\n" +
                    "                                                           join Mentor \n" +
                    "                                                            on Account.id = Mentor.account_id\n" +
                    "                                                            join Wish_List\n" +
                    "                                                            on Mentor.account_id = Wish_List.mentor_id\n" +
                    "                                                           join Status\n" +
                    "                                                         on Status.id = Wish_List.status_id\n" +
                    "                                                           where Wish_List.mentee_id = ? and Wish_List.status_id = 11";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getString("ac_id"));
                account.setName(resultSet.getString("name"));
                account.setEmail(resultSet.getString("mail"));
                account.setAvatar(resultSet.getString("avatar"));

                Mentor mentor = new Mentor();
                mentor.setAccount(account);

                Level_SkillDAO level_skillDAO = new Level_SkillDAO();
                ArrayList<Level_Skills> level_skills = level_skillDAO.getLevel_SkillByMentorId(mentor.getAccount().getId());
                mentor.setLevel_skills(level_skills);

                Status status = new Status();
                status.setType(resultSet.getString("type"));
                WishList wishList = new WishList();
                wishList.setMentor(mentor);
                wishList.setStatus(status);
                wishList.setId(resultSet.getInt("id"));
                wishList.setTimeRequest(resultSet.getDate("date"));
                wishLists.add(wishList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishLists;
    }

    public void UnfollowById(int wish_list_id){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Wish_List]\n" +
                    "   SET \n" +
                    "      [status_id] = ?,\n" +
                    "      [date] = GETDATE()\n" +
                    " WHERE Wish_List.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.CANCEL);
            preparedStatement.setInt(2,wish_list_id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void followAgain(String id_mentee, String id_mentor){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "UPDATE [dbo].[Wish_List]\n" +
                    "                      SET \n" +
                    "                        [status_id] = ?,\n" +
                    "                         [date] = GETDATE()\n" +
                    "                    WHERE Wish_List.mentor_id = ? AND Wish_List.mentee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StatusEnum.ACCEPTED);
            preparedStatement.setString(2, id_mentor);
            preparedStatement.setString(3, id_mentee);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void Follow(String id_mentee, String id_mentor ){
        try {
            Connection connection = JDBC.getConnection();
            String sql = "INSERT INTO [dbo].[Wish_List]\n" +
                    "           ([mentor_id]\n" +
                    "           ,[mentee_id]\n" +
                    "           ,[status_id]\n" +
                    "           ,[date])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,GETDATE())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id_mentor);
            preparedStatement.setString(2, id_mentee);
            preparedStatement.setInt(3, StatusEnum.ACCEPTED);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Unfollow(String mentee_id, String mentor_id){
            try{
                String sql = "UPDATE [dbo].[Wish_List]\n" +
                        "   SET [status_id] = ?\n" +
                        "      ,[date] = GetDate()\n" +
                        " WHERE mentee_id = ? AND mentor_id = ?";
                Connection connection = JDBC.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, StatusEnum.CANCEL);
                preparedStatement.setString(2, mentee_id);
                preparedStatement.setString(3, mentor_id);
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public WishList CheckFollow(String mentee_id, String mentor_id) {
        String sql = "SELECT id, mentor_id, mentee_id, status_id FROM Wish_List WHERE mentee_id = ? AND mentor_id = ?";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, mentee_id);
            preparedStatement.setString(2, mentor_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                WishList wishList = new WishList();
                wishList.setId(resultSet.getInt("id"));

                Account acc_mentor = new Account();
                acc_mentor.setId(resultSet.getString("mentor_id"));
                wishList.setMentor(new Mentor(acc_mentor));

                Account acc_mentee = new Account();
                acc_mentee.setId(resultSet.getString("mentee_id"));
                wishList.setMentee(new Mentee(acc_mentee));

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                wishList.setStatus(status);

                return wishList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<WishList> getOfMentee(String mentee_id) {
        ArrayList<WishList> wishLists = new ArrayList<>();

        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT [mentor_id]\n" +
                    "      ,[mentee_id]\n" +
                    "      ,[status_id]\n" +
                    "      ,[date]\n" +
                    "      ,[id]\n" +
                    "  FROM [dbo].[Wish_List]" +
                    "WHERE mentee_id = ? And status_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mentee_id);
            preparedStatement.setInt(2, StatusEnum.ACCEPTED);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WishList wishList = new WishList();
                wishList.setId(resultSet.getInt("id"));

                Account acc_mentor = new Account();
                acc_mentor.setId(resultSet.getString("mentor_id"));

                Mentor mentor = new Mentor();
                mentor.setAccount(acc_mentor);
                wishList.setMentor(mentor);


                Account acc_mentee = new Account();
                acc_mentee.setId(resultSet.getString("mentee_id"));

                Mentee mentee = new Mentee();
                mentee.setAccount(acc_mentee);
                wishList.setMentee(mentee);

                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                wishList.setStatus(status);
                wishList.setTimeRequest(resultSet.getDate("date"));

                wishLists.add(wishList);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishLists;
    }

    public ArrayList<WishList> getByMentorId(String id) {
        ArrayList<WishList> wishLists = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            String sql = "SELECT [mentor_id]\n" +
                    "      ,[mentee_id]\n" +
                    "      ,[status_id]\n" +
                    "      ,[date]\n" +
                    "      ,[id]\n" +
                    "  FROM [dbo].[Wish_List]\n" +
                    "  Where mentor_id = ? And status_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, StatusEnum.ACCEPTED);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                WishList wishList = new WishList();
                wishList.setId(resultSet.getInt("id"));
                Account acc_mentee = new Account();
                acc_mentee.setId(resultSet.getString("mentee_id"));
                wishList.setMentee(new Mentee(acc_mentee));
                Account acc_mentor = new Account();
                acc_mentor.setId(resultSet.getString("mentor_id"));
                wishList.setMentor(new Mentor(acc_mentor));
                Status status = new Status();
                status.setId(resultSet.getInt("status_id"));
                wishList.setStatus(status);
                wishList.setTimeRequest(resultSet.getDate("date"));
                wishLists.add(wishList);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return wishLists;
    }

}
