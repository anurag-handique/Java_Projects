package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    // Static connection object
    private static Connection connection = null;

    // Get a database connection
    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FileEncryptionPlus?useSSL=false", "root", "60039198736900864982");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Database connection request successful!");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        return connection;
    }

    // Close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                System.out.println("Database connection closed successfully!");
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
