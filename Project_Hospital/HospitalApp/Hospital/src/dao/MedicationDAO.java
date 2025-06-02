package dao;

import database.DBConnection;
import model.Medication;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO {
    public List<Medication> getAllMedications() {
        List<Medication> list = new ArrayList<>();
        String sql = "SELECT * FROM Medications";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medication m = new Medication();
                m.setPrescriptionID(rs.getInt("prescriptionID"));
                m.setMedicationName(rs.getString("medicationName"));
                m.setDosageInstruction(rs.getString("dosageInstruction"));
                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addMedication(Medication m) {
        String sql = "INSERT INTO Medications(prescriptionID, medicationName, dosageInstruction) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getPrescriptionID());
            stmt.setString(2, m.getMedicationName());
            stmt.setString(3, m.getDosageInstruction());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
