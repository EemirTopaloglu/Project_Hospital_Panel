package dao;

import database.DBConnection;
import model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = """
            SELECT u.userID, u.fullName, u.email, u.phone, u.address,
                   d.specialty, d.workingIn
            FROM User u JOIN Doctor d ON u.userID = d.userID
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setUserID(rs.getInt("userID"));
                d.setFullName(rs.getString("fullName"));
                d.setEmail(rs.getString("email"));
                d.setPhone(rs.getString("phone"));
                d.setAddress(rs.getString("address"));
                d.setSpecialty(rs.getString("specialty"));
                d.setWorkingIn(rs.getInt("workingIn"));
                doctors.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public boolean addDoctor(Doctor d) {
        String userSQL = "INSERT INTO User(userID, email, password, fullName, phone, address, isDoctor) VALUES (?, ?, ?, ?, ?, ?, TRUE)";
        String docSQL = "INSERT INTO Doctor(userID, workingIn, specialty) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement uStmt = conn.prepareStatement(userSQL);
                 PreparedStatement dStmt = conn.prepareStatement(docSQL)) {

                uStmt.setInt(1, d.getUserID());
                uStmt.setString(2, d.getEmail());
                // Use the provided password 
                uStmt.setString(3, d.getPassword() != null && !d.getPassword().trim().isEmpty() 
                                    ? d.getPassword() : "defaultpass");
                uStmt.setString(4, d.getFullName());
                uStmt.setString(5, d.getPhone());
                uStmt.setString(6, d.getAddress());
                uStmt.executeUpdate();

                dStmt.setInt(1, d.getUserID());
                dStmt.setInt(2, d.getWorkingIn());
                dStmt.setString(3, d.getSpecialty());
                dStmt.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteDoctor(int userID) {
        try (Connection conn = DBConnection.getConnection()) {
            String delDoctor = "DELETE FROM Doctor WHERE userID=?";
            String delUser = "DELETE FROM User WHERE userID=?";

            try (PreparedStatement dStmt = conn.prepareStatement(delDoctor);
                 PreparedStatement uStmt = conn.prepareStatement(delUser)) {

                dStmt.setInt(1, userID);
                uStmt.setInt(1, userID);

                dStmt.executeUpdate();
                uStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}