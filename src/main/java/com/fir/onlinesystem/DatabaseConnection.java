package com.fir.onlinesystem;
import java.sql.*;
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fir_system"; // Update DB name if different
    private static final String DB_USER = "root";       // Change to your MySQL username
    private static final String DB_PASSWORD = "S@lu7034"; // Change to your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }
}
