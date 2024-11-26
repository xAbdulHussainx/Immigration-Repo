package edu.gmu.cs321;

import java.sql.*;

import com.mysql.cj.protocol.Resultset;

public class TestDatabase {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/cs321";
    private static final String USER = "root";
    private static final String PASSWORD = "pass"; // replace with your MySQL password

    // JDBC variables for opening, closing connection and statement
    private static Connection connection;
    private static Statement statement;

    // Method to establish connection
    public static Connection getConnection() {
        try {
            // Load and register MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to create a new Sponsor
    public static int createSponsor(String fullName, Date dob, String gender, String nationality, String phone, String email, String relationship, String caseType, String sponsorshipReason, String applicantName, Date applicantDob, String intendedStay, String uniqID, String sponsor_income, boolean isAgreed, String address) {
        String insertQuery = "INSERT INTO sponsor (full_name, dob, gender, nationality, phone, email, relationship, case_type, sponsorship_reason, applicant_name, applicant_dob, intended_stay, unique_id, sponsor_income, agreement_accepted, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fullName);
            stmt.setDate(2, dob);
            stmt.setString(3, gender);
            stmt.setString(4, nationality);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, relationship);
            stmt.setString(8, caseType);
            stmt.setString(9, sponsorshipReason);
            stmt.setString(10, applicantName);
            stmt.setDate(11, applicantDob);
            stmt.setString(12, intendedStay);
            stmt.setString(13, uniqID);
            stmt.setString(14, sponsor_income);
            stmt.setBoolean(15, isAgreed);
            stmt.setString(16, address);

            int rowsAffected = stmt.executeUpdate();
            int id = 0;
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    id = rs.getInt(1);
                    return id;
                }
            } else {
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean fetchApplicationsForEmployee(int employeeId, List<String> applications) {
        String query = "SELECT application_id FROM workflow WHERE employee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            applications.clear();
            while (rs.next()) {
                applications.add(rs.getString("application_id"));
            }
            return !applications.isEmpty();
        } catch (SQLException e) {
            System.err.println("Error fetching applications for employee: " + e.getMessage());
            return false;
        }
    }

    public void updateApplicationStatus(String applicationId, String status) {
        String query = "UPDATE workflow SET status = ? WHERE application_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setString(2, applicationId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Application status updated successfully.");
            } else {
                System.out.println("No application found with ID: " + applicationId);
            }
        } catch (SQLException e) {
            System.err.println("Error updating application status: " + e.getMessage());
        }
    }

    public void fetchApplicationDetails(String applicationId, StringBuilder details) {
        String query = "SELECT * FROM applications WHERE application_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, applicationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                details.setLength(0);
                details.append("ID: ").append(rs.getString("application_id"))
                       .append(", Name: ").append(rs.getString("applicant_name"))
                       .append(", Status: ").append(rs.getString("status"));
            } else {
                details.setLength(0);
                details.append("Application not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching application details: " + e.getMessage());
        }
    }
}

