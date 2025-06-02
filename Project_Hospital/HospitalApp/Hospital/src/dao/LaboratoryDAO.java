package dao;

import database.DBConnection;
import model.Laboratory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryDAO {
    public List<Laboratory> getAllLabs() {
        List<Laboratory> list = new ArrayList<>();
        String sql = "SELECT * FROM Laboratory";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Laboratory l = new Laboratory();
                l.setLabID(rs.getInt("labID"));
                l.setName(rs.getString("name"));
                l.setConnectedDept(rs.getInt("connectedDept"));
                l.setPhone(rs.getString("phone"));
                l.setRespDoctorID(rs.getInt("respDoctorID"));
                l.setRespSID(rs.getInt("respSID"));
                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addLab(Laboratory l) {
        String sql = "INSERT INTO Laboratory(labID, name, connectedDept, phone, respDoctorID, respSID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getLabID());
            stmt.setString(2, l.getName());
            stmt.setInt(3, l.getConnectedDept());
            stmt.setString(4, l.getPhone());
            stmt.setInt(5, l.getRespDoctorID());
            stmt.setInt(6, l.getRespSID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLab(int labID) {
        String sql = "DELETE FROM Laboratory WHERE labID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, labID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
