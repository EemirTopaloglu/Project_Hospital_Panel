package dao;

import database.DBConnection;
import model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM Report";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Report r = new Report();
                r.setReportID(rs.getInt("reportID"));
                r.setDiagnosis(rs.getString("diagnosis"));
                r.setBilling(rs.getDouble("billing"));
                reports.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    public boolean addReport(Report r) {
        String sql = "INSERT INTO Report(reportID, diagnosis, billing) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getReportID());
            stmt.setString(2, r.getDiagnosis());
            stmt.setDouble(3, r.getBilling());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
