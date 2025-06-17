package entities;

import utils.DatabaseConnection;

import java.sql.*;

public class user {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;

    // Constructors
    public user(String username, String email, String password, String userType, String fullName, String phoneNumber, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public user(String username, String email, String password, String fullName, String phoneNumber, String address) {
        this(username, email, password, null, fullName, phoneNumber, address);
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }


    // Save user to database
    public void saveToDatabase() {
        String sql = "INSERT INTO users (username, email, acc_password, full_name, phone_number, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, this.username);
            ps.setString(2, this.email);
            ps.setString(3, this.password);
            ps.setString(5, this.fullName);
            ps.setString(6, this.phoneNumber);
            ps.setString(7, this.address);

            ps.executeUpdate();
            System.out.println("User saved successfully.");

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Get userId by username
    public static int getUserIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        int userId = -1;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching user ID: " + e.getMessage());
        }

        return userId;
    }

    // Display user details
    public void display() {
        System.out.println("------ User Details ------");
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Full Name: " + fullName);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("--------------------------");
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof user)) return false;

        user user = (user) o;

        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
