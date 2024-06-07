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

    public static void main(String[] args) {
        SlotDAO slotDAO = new SlotDAO();
        ArrayList<Slot> slots  = slotDAO.selectAll();
        for (Slot slot : slots) {
            System.out.println(slot.getId() + " " + slot.getStart_at() + " " + slot.getEnd_at() );
        }
    }
}

