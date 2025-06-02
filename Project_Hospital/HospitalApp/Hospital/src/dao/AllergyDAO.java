package dao;

import database.DBConnection;
import model.Allergy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllergyDAO {
    public List<Allergy> getAllergies() {
        List<Allergy> list = new ArrayList<>();
        String sql = "SELECT * FROM Allergies";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Allergy a = new Allergy();
                a.setUserID(rs.getInt("userID"));
                a.setAlID(rs.getInt("alID"));
                a.setAllergyName(rs.getString("allergyName"));
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addAllergy(Allergy a) {
        String sql = "INSERT INTO Allergies(userID, alID, allergyName) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getUserID());
            stmt.setInt(2, a.getAlID());
            stmt.setString(3, a.getAllergyName());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllergy(int userID, int alID) {
        String sql = "DELETE FROM Allergies WHERE userID=? AND alID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, alID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
