package edu.gmu.cs321;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

public class EmployeeTest {

    private TestDatabase database;

    @Before
    public void setup() {
        database = new TestDatabase();
    }

    @Test
    public void testApproveApplication() {
        String selectedStatus = "Approved";
        
        // Fetch all applications
        List<String[]> applications = database.fetchAllApplications();
        if (applications.isEmpty()) {
            fail("No applications available in the database for testing.");
        }

        // Get the first application ID
        String applicationId = applications.get(0)[0];

        database.updateApplicationStatus(applicationId, selectedStatus);
        String status = TestDatabase.getSponsorStatus(applicationId);

        assertEquals("Application status was not updated to Approved.", "Approved", status);
    }

    @Test
    public void testDenyApplication() {
        String selectedStatus = "Denied";
        
        // Fetch all applications
        List<String[]> applications = database.fetchAllApplications();
        if (applications.isEmpty()) {
            fail("No applications available in the database for testing.");
        }

        // Get the first application ID
        String applicationId = applications.get(0)[0];

        database.updateApplicationStatus(applicationId, selectedStatus);
        String status = TestDatabase.getSponsorStatus(applicationId);

        assertEquals("Application status was not updated to Denied.", "Denied", status);
    }

    @Test
    public void testSendUpdateMessage() {
        String selectedStatus = "In Progress";
        
        // Fetch all applications
        List<String[]> applications = database.fetchAllApplications();
        if (applications.isEmpty()) {
            fail("No applications available in the database for testing.");
        }

        // Get the first application ID
        String applicationId = applications.get(0)[0];

        database.updateApplicationStatus(applicationId, selectedStatus);
        String status = TestDatabase.getSponsorStatus(applicationId);

        assertEquals("Application status was not updated to In Progress.", "In Progress", status);
    }

    @Test
    public void testFilterApplicationsByStatus() {
        String status = "Approved";
        List<String> filteredApplications = new ArrayList<>();

        boolean isFiltered = database.fetchApplicationsByStatus(status, filteredApplications);

        assertTrue("No applications were filtered by status: Approved.", isFiltered);
        assertFalse("Filtered applications list is empty.", filteredApplications.isEmpty());
    }
}
