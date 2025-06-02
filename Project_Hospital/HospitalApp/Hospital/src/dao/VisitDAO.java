package dao;

import database.DBConnection;
import model.Visit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitDAO {
    public List<Visit> getAllVisits() {
        List<Visit> visits = new ArrayList<>();
        String sql = "SELECT * FROM Visit";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Visit v = new Visit();
                v.setPatientID(rs.getInt("patientID"));
                v.setDoctorID(rs.getInt("doctorID"));
                v.setDate(rs.getDate("date"));
                v.setTime(rs.getString("time"));
                v.setStatus(rs.getString("status"));
                v.setReportID(rs.getInt("reportID") != 0 ? rs.getInt("reportID") : null);
                visits.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visits;
    }

    public boolean addVisit(Visit v) {
        String sql = "INSERT INTO Visit(patientID, doctorID, date, time, status, reportID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, v.getPatientID());
            stmt.setInt(2, v.getDoctorID());
            stmt.setDate(3, new java.sql.Date(v.getDate().getTime()));
            stmt.setTime(4, Time.valueOf(v.getTime() + ":00"));
            stmt.setString(5, v.getStatus());
            if (v.getReportID() == null) stmt.setNull(6, Types.INTEGER);
            else stmt.setInt(6, v.getReportID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVisit(Visit v) {
        String sql = "DELETE FROM Visit WHERE patientID=? AND doctorID=? AND date=? AND time=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, v.getPatientID());
            stmt.setInt(2, v.getDoctorID());
            stmt.setDate(3, new java.sql.Date(v.getDate().getTime()));
            stmt.setTime(4, Time.valueOf(v.getTime() + ":00"));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
