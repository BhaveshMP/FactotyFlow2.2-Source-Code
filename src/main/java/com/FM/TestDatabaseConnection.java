package com.FM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        // Database connection details
    	String url = "jdbc:mysql://34.93.153.70:3306/FactoryManager2"; // Replace with the actual public IP

        String username = "root";
        String password = "admin";
        try {
            // Establishing the connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");
            // Test your SQL queries here (e.g., SELECT)
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

