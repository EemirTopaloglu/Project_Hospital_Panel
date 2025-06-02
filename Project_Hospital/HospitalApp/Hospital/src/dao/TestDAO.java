package dao;

import database.DBConnection;
import model.TestResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    public List<TestResult> getAllTests() {
        List<TestResult> tests = new ArrayList<>();
        String sql = "SELECT * FROM Test";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TestResult t = new TestResult();
                t.setPatientID(rs.getInt("patientID"));
                t.setDoctorID(rs.getInt("doctorID"));
                t.setDate(rs.getDate("date"));
                t.setTime(rs.getString("time"));
                t.setName(rs.getString("name"));
                t.setLabID(rs.getInt("labID"));
                t.setResult(rs.getString("result"));
                t.setPrescriptionID(rs.getInt("prescriptionID"));
                tests.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    public boolean addTest(TestResult t) {
        String sql = "INSERT INTO Test(patientID, doctorID, date, time, name, labID, result, prescriptionID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getPatientID());
            stmt.setInt(2, t.getDoctorID());
            stmt.setDate(3, new java.sql.Date(t.getDate().getTime()));
            stmt.setTime(4, Time.valueOf(t.getTime() + ":00"));
            stmt.setString(5, t.getName());
            stmt.setInt(6, t.getLabID());
            stmt.setString(7, t.getResult());
            stmt.setInt(8, t.getPrescriptionID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
