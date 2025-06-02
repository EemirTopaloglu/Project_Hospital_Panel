package dao;

import database.DBConnection;
import model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientDAO {
	//Retrieves patient records by joining User and Patient tables 
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = """
            SELECT u.userID, u.fullName, u.email, u.phone, u.address,
                   p.birthDate, p.gender, p.insurancePolId
            FROM User u JOIN Patient p ON u.userID = p.userID
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Patient p = new Patient();
                p.setUserID(rs.getInt("userID"));
                p.setFullName(rs.getString("fullName"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                p.setBirthDate(rs.getDate("birthDate"));
                p.setGender(rs.getString("gender"));
                p.setInsurancePolId(rs.getString("insurancePolId"));
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
    
    //Adds new patient into both User and Patient tables
    public boolean addPatient(Patient p) {
        String userSQL = "INSERT INTO User(userID, email, password, fullName, phone, address, isPatient) VALUES (?, ?, ?, ?, ?, ?, TRUE)";
        String patientSQL = "INSERT INTO Patient(userID, birthDate, gender, insurancePolId) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement uStmt = conn.prepareStatement(userSQL);
                 PreparedStatement pStmt = conn.prepareStatement(patientSQL)) {

                uStmt.setInt(1, p.getUserID());
                uStmt.setString(2, p.getEmail());
                // Use the actual password from the Patient object instead of hardcoded value
                uStmt.setString(3, p.getPassword() != null && !p.getPassword().trim().isEmpty() 
                                    ? p.getPassword() : "defaultpass");
                uStmt.setString(4, p.getFullName());
                uStmt.setString(5, p.getPhone());
                uStmt.setString(6, p.getAddress());
                uStmt.executeUpdate();

                pStmt.setInt(1, p.getUserID());
                pStmt.setDate(2, new java.sql.Date(p.getBirthDate().getTime()));
                pStmt.setString(3, p.getGender());
                pStmt.setString(4, p.getInsurancePolId());
                pStmt.executeUpdate();

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

    //Deletes patient record across related tables
    public boolean deletePatient(int userID) {
        try (Connection conn = DBConnection.getConnection()) {
            String deletePatient = "DELETE FROM Patient WHERE userID=?";
            String deleteUser = "DELETE FROM User WHERE userID=?";

            try (PreparedStatement pStmt = conn.prepareStatement(deletePatient);
                 PreparedStatement uStmt = conn.prepareStatement(deleteUser)) {

                pStmt.setInt(1, userID);
                uStmt.setInt(1, userID);

                pStmt.executeUpdate();
                uStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Updates a specific field for a patient
    public boolean updatePatientField(int userID, String column, String newValue) {
        String userColumnQuery = "UPDATE User SET " + column + " = ? WHERE userID = ?";
        String patientColumnQuery = "UPDATE Patient SET " + column + " = ? WHERE userID = ?";

        // Added password to user columns array
        String[] userCols = {"fullName", "email", "phone", "address", "password"};
        String[] patientCols = {"birthDate", "gender", "insurancePolId"};

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt;
            if (Arrays.asList(userCols).contains(column)) {
                stmt = conn.prepareStatement(userColumnQuery);
            } else if (Arrays.asList(patientCols).contains(column)) {
                stmt = conn.prepareStatement(patientColumnQuery);
            } else {
                return false; // Invalid column
            }

            stmt.setString(1, newValue);
            stmt.setInt(2, userID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}