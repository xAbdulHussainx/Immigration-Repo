package edu.gmu.cs321;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.protocol.Resultset;

public class TestDatabase {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/cs321";
    private static final String USER = "root";
    private static final String PASSWORD = "xxx"; // replace with your MySQL password

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

    public List<String[]> fetchAllApplications() {
        List<String[]> applications = new ArrayList<>();
        String query = "SELECT unique_id, full_name FROM sponsor";
        try (Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String applicationId = rs.getString("unique_id");
                String fullName = rs.getString("full_name");
                applications.add(new String[]{applicationId, fullName}); // Add ID and name to the list
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all applications: " + e.getMessage());
        }
        return applications;
    }


    public static boolean fetchAllFieldsById(String applicationId, StringBuilder details) {
        String query = "SELECT full_name, dob, gender, nationality, phone, email, relationship, case_type, " +
                       "sponsorship_reason, applicant_name, applicant_dob, intended_stay, sponsor_income, status, address " +
                       "FROM sponsor WHERE unique_id = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setString(1, applicationId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                details.setLength(0); // Clear existing details
                details.append("Full Name: ").append(rs.getString("full_name"))
                       .append("\nDate of Birth: ").append(rs.getDate("dob"))
                       .append("\nGender: ").append(rs.getString("gender"))
                       .append("\nNationality: ").append(rs.getString("nationality"))
                       .append("\nPhone: ").append(rs.getString("phone"))
                       .append("\nEmail: ").append(rs.getString("email"))
                       .append("\nRelationship: ").append(rs.getString("relationship"))
                       .append("\nCase Type: ").append(rs.getString("case_type"))
                       .append("\nReason for Sponsorship: ").append(rs.getString("sponsorship_reason"))
                       .append("\nApplicant Name: ").append(rs.getString("applicant_name"))
                       .append("\nApplicant Date of Birth: ").append(rs.getDate("applicant_dob"))
                       .append("\nIntended Stay: ").append(rs.getString("intended_stay"))
                       .append("\nSponsor Income: ").append(rs.getString("sponsor_income"))
                       .append("\nStatus: ").append(rs.getString("status"))
                       .append("\nAddress: ").append(rs.getString("address"));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all fields by ID: " + e.getMessage());
            details.setLength(0);
            details.append("Error fetching application details.");
            return false;
        }
        details.setLength(0);
        details.append("Application not found.");
        return false;
    }
    



    public boolean fetchApplicationDetailsById(String applicationId, StringBuilder details) {
        String query = "SELECT full_name, dob, gender, nationality, phone, email, relationship, case_type, sponsorship_reason, applicant_name, applicant_dob, intended_stay, sponsor_income, status, address "
                     + "FROM sponsor WHERE unique_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setString(1, applicationId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                details.setLength(0); // Clear existing details
                details.append("Full Name: ").append(rs.getString("full_name"))
                       .append("\nDate of Birth: ").append(rs.getDate("dob"))
                       .append("\nGender: ").append(rs.getString("gender"))
                       .append("\nNationality: ").append(rs.getString("nationality"))
                       .append("\nPhone: ").append(rs.getString("phone"))
                       .append("\nEmail: ").append(rs.getString("email"))
                       .append("\nRelationship: ").append(rs.getString("relationship"))
                       .append("\nCase Type: ").append(rs.getString("case_type"))
                       .append("\nReason for Sponsorship: ").append(rs.getString("sponsorship_reason"))
                       .append("\nApplicant Name: ").append(rs.getString("applicant_name"))
                       .append("\nApplicant Date of Birth: ").append(rs.getDate("applicant_dob"))
                       .append("\nIntended Stay: ").append(rs.getString("intended_stay"))
                       .append("\nSponsor Income: ").append(rs.getString("sponsor_income"))
                       .append("\nStatus: ").append(rs.getString("status"))
                       .append("\nAddress: ").append(rs.getString("address"));
                return true;
            } else {
                details.setLength(0);
                details.append("Application not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching application details by ID: " + e.getMessage());
            details.setLength(0);
            details.append("Error fetching application details.");
            return false;
        }
    }
    

    // Fetch applications with their statuses
    public boolean fetchApplicationsWithStatus(List<String[]> applications) {
        String query = "SELECT unique_id, status FROM sponsor";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            applications.clear();
            while (rs.next()) {
                String id = rs.getString("unique_id");
                String status = rs.getString("status");
                applications.add(new String[] { id, status });
            }
            return !applications.isEmpty();
        } catch (SQLException e) {
            System.err.println("Error fetching applications: " + e.getMessage());
            return false;
        }
    }

    // Fetch applications filtered by status
    public boolean fetchApplicationsByStatus(String status, List<String> applications) {
        String query = "SELECT unique_id FROM sponsor WHERE status = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            applications.clear();
            while (rs.next()) {
                applications.add(rs.getString("unique_id"));
            }
            return !applications.isEmpty();
        } catch (SQLException e) {
            System.err.println("Error fetching applications by status: " + e.getMessage());
            return false;
        }
    }

    // Update application status (approve, deny, or in progress)
    public void updateApplicationStatus(String applicationId, String selectedStatus) {
        String query = "UPDATE sponsor SET status = ? WHERE unique_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, selectedStatus);
            stmt.setString(2, applicationId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Application status updated to: " + selectedStatus);
            } else {
                System.out.println("No application found with ID: " + applicationId);
            }
        } catch (SQLException e) {
            System.err.println("Error updating application status: " + e.getMessage());
        }
    }


        public static String getSponsorStatus(String sponsorId) {
        String query = "SELECT status FROM sponsor WHERE unique_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sponsorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("status");
            } else {
                return null; // Sponsor ID not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean validateEmployerCredentials(String username, String password) {
        String query = "SELECT Password FROM Employer WHERE Username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String dbPassword = rs.getString("Password");
                return dbPassword.equals(password);
            }
            return false; // Username not found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}