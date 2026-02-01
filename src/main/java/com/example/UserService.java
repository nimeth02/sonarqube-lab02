package main.java.com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    // Create logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    // FIXED: Remove hardcoded credentials - should come from configuration/environment
    private final String password;
    
    // Constructor to accept credentials
    public UserService(String password) {
        this.password = password;
    }
    
    // Alternative: Use environment variable or configuration file
    public UserService() {
        this.password = System.getenv("DB_PASSWORD");
        if (this.password == null || this.password.isEmpty()) {
            throw new IllegalStateException("Database password not configured");
        }
    }
    
    // FIXED: SQL Injection vulnerability and resource management
    public void findUser(String username) throws SQLException {
        String url = "jdbc:mysql://localhost/db";
        String dbUser = "root";
        
        // Use try-with-resources for automatic resource management
        try (Connection conn = DriverManager.getConnection(url, dbUser, password);
             PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE name = ?")) {
            
            // Set parameter to prevent SQL injection
            st.setString(1, username);
            
            // Execute query
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    // Process results here
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    logger.info("Found user: ID={}, Name={}", id, name);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Error finding user: {}", username, e);
            throw e;
        }
    }
    
    // FIXED: Use logger instead of System.out
    public void notUsed() {
        logger.info("I am never called");
    }
}
