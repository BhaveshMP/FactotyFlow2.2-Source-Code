package com.FM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection5 {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/FactoryManager2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    // Method to get the database connection
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Unable to load JDBC driver.");
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

