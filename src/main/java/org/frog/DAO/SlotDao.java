package org.frog.DAO;

import org.frog.model.Slot;


import java.sql.*;
import java.util.ArrayList;

public class SlotDAO {

    public ArrayList<Slot> selectAll() {
        ArrayList<Slot> slots = new ArrayList<>();
        try{
            Connection connection = JDBC.getConnection();
            String sql = "Select id, time_start, time_end\n" +
                    "from Slot";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Slot slot = new Slot();
                slot.setId(resultSet.getInt("id"));
                slot.setStart_at(resultSet.getString("time_start"));
                slot.setEnd_at(resultSet.getString("time_end"));
                slots.add(slot);
            }
            JDBC.closeConnection(connection);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return slots;
    }
    public Slot getTimeSlot(int id) {
        Slot slot = new Slot();
        try{
            String sql = "SELECT time_start,time_end FROM Slot\n" +
                    "WHERE id = ?";
            PreparedStatement stm = JDBC.getConnection().prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                slot.setStart_at(rs.getString("time_start"));
                slot.setEnd_at(rs.getString("time_end"));
                return slot;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return slot;
    }


}

