package com.fir.onlinesystem; // This MUST be at the very top!

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Method 1: Register
    public boolean registerCitizen(String username, String password) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Citizen')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Method 2: Login (This is the one it was missing!)
    public String login(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            // If it finds a match, it returns "Admin" or "Citizen"
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            System.err.println("Login query failed: " + e.getMessage());
        }
        return null; // Returns null if login fails
    }
}