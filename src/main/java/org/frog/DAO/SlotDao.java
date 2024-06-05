package org.frog.DAO;

import org.frog.model.Slot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SlotDao {
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
