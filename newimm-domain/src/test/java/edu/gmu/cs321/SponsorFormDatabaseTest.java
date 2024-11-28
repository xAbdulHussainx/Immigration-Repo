package edu.gmu.cs321;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Random;

public class SponsorFormDatabaseTest {

    private String unique_id;

    // JUnit 4 uses @Before instead of @BeforeAll
    @Before
    public void setupDatabaseConnection() {
        assertNotNull("Database connection should be established", TestDatabase.getConnection());
    }

    @Test
    public void testSponsorFormSubmission() {
        // Generate test data
        String fullName = "John Doe";
        Date dob = Date.valueOf("1980-05-15");
        String gender = "Male";
        String nationality = "USA";
        String phone = "1234567890";
        String email = "johndoe@example.com";
        String relationship = "Brother";
        String caseType = "Family-based";
        String sponsorshipReason = "Support for immigration process";
        String applicantName = "Jane Doe";
        Date applicantDob = Date.valueOf("1990-07-20");
        String intendedStay = "6 months";
        String sponsorIncome = "$100,000";
        boolean isAgreed = true;
        String address = "1234 Elm Street, Springfield, USA";

        // Generate a unique ID
        Random random = new Random();
        unique_id = String.valueOf(100000 + random.nextInt(900000));

        // Insert data into the database using the createSponsor method
        int sponsorId = TestDatabase.createSponsor(fullName, dob, gender, nationality, phone, email,
        relationship, caseType, sponsorshipReason, applicantName, applicantDob, intendedStay,
        unique_id, sponsorIncome, isAgreed, address);

        assertTrue("Sponsor should be created successfully", sponsorId > 0);
    }

    
}
