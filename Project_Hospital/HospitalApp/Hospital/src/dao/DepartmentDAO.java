package dao;

import database.DBConnection;
import model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Department";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department d = new Department();
                d.setDepartmentID(rs.getInt("departmentID"));
                d.setName(rs.getString("name"));
                d.setSpecialty(rs.getString("specialty"));
                d.setMedManagerID(rs.getInt("medManagerID"));
                d.setAdManagerID(rs.getInt("adManagerID"));
                departments.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public boolean addDepartment(Department d) {
        String sql = "INSERT INTO Department(departmentID, name, specialty, medManagerID, adManagerID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getDepartmentID());
            stmt.setString(2, d.getName());
            stmt.setString(3, d.getSpecialty());
            stmt.setInt(4, d.getMedManagerID());
            stmt.setInt(5, d.getAdManagerID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(int id) {
        String sql = "DELETE FROM Department WHERE departmentID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
