package dao;

import database.DBConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public User authenticate(String email, String password) {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setDoctor(rs.getBoolean("isDoctor"));
                u.setPatient(rs.getBoolean("isPatient"));
                u.setStaff(rs.getBoolean("isStaff"));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // kullanıcı bulunamadıysa null döner
    }

    public java.util.List<User> getAllUsers() {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT * FROM User";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setDoctor(rs.getBoolean("isDoctor"));
                u.setPatient(rs.getBoolean("isPatient"));
                u.setStaff(rs.getBoolean("isStaff"));
                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}