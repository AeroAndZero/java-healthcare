package com.project.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static Connection connection;

    // Load properties and initialize the database connection
    public static Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("db.properties file not found in the classpath");
                }

                Properties props = new Properties();
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                if (url == null || user == null || password == null) {
                    throw new RuntimeException("Missing database connection properties");
                }

                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the database connection", e);
            }
        }
        return connection;
    }

    // Check if the connection is closed
    private static boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    // Close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Optionally log the error or rethrow it
                e.printStackTrace();
            }
        }
    }
}
