package com.fir.onlinesystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    // This method takes the data from the website and saves it to MySQL
    public boolean addComplaint(String category, String location, String description) {
        // Assuming your table is named 'complaints' and has these columns. 
        // We set the default status to 'Pending'
        String query = "INSERT INTO complaints (category, location, description, status) VALUES (?, ?, ?, 'Pending')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, category);
            stmt.setString(2, location);
            stmt.setString(3, description);
            
            // executeUpdate returns the number of rows affected. If > 0, it worked!
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding complaint: " + e.getMessage());
            return false;
        }
    }
    // Make sure you have these imports at the very top of the file!
    // import java.util.ArrayList;
    // import java.util.List;
    // import java.sql.ResultSet;

    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String query = "SELECT * FROM complaints";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Complaint c = new Complaint();
                c.setId(rs.getInt("id"));
                c.setCategory(rs.getString("category"));
                c.setLocation(rs.getString("location"));
                c.setDescription(rs.getString("description"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving complaints: " + e.getMessage());
        }
        return list;
    }
    // This method updates the status of a specific complaint
    public boolean updateComplaintStatus(int id, String newStatus) {
        String query = "UPDATE complaints SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating complaint status: " + e.getMessage());
            return false;
        }
    }
}