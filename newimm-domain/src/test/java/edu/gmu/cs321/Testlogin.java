package edu.gmu.cs321;

import org.junit.Test;
import static org.junit.Assert.*;

public class Testlogin {

    @Test
    public void testSponsorLoginSuccess() {
        // Simulating a valid sponsor login
        String sponsorId = "validSponsorId";
        String status = TestDatabase.getSponsorStatus(sponsorId);

        // Check if the status is not null and matches the expected value
        assertNotNull("The sponsor status should not be null for a valid sponsor ID.", status);
        assertEquals("The sponsor status should be 'Approved' for validSponsorId.", "Approved", status);
    }

    @Test
    public void testEmployerLoginSuccess() {
        // Simulating a valid employer login
        String username = "validUser";
        String password = "validPass";

        boolean isValid = TestDatabase.validateEmployerCredentials(username, password);

        // Check if the login credentials are valid
        assertTrue("Employer credentials should be valid for the provided username and password.", isValid);
    }
}
