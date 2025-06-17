package db_objs;

import entities.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class MyJDBC {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/HackSquadEcommerce_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Root";

    private static Connection connection;
    // Get or create DB connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Validate user login
    public static User validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE LOWER(username) = LOWER(?) AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.out.println("DB connection failed.");
                return null;
            }

            stmt.setString(1, username.trim());
            stmt.setString(2, password.trim());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String phone = rs.getString("phone_number");
                String address = rs.getString("address");
                String email = rs.getString("email");

                User loggedInUser = new User(user_id, username, email, password, fullName, phone, address);
                Session.setCurrentUser(loggedInUser);

                System.out.println("Login successful for user: " + fullName);
                return loggedInUser;
            } else {
                System.out.println("No user found with those credentials.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hash password with SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Register new user
    public static boolean register(String username, String email, String password, String fullName, String phone, String address) {
        String sql = "INSERT INTO users (username, email, password, full_name, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // Store plain password - consider hashing for security
            stmt.setString(4, fullName);
            stmt.setString(5, phone);
            stmt.setString(6, address);

            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // Username or email already exists
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if username exists
    private static boolean checkUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
