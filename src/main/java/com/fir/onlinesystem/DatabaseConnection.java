package com.fir.onlinesystem; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    public static Connection getConnection() {
        try {
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
        catch (SQLException e) {
            System.out.println("Database connection failed!");
            return null;
        }
    }
}
