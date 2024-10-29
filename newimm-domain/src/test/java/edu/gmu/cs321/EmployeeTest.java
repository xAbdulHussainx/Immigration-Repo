package edu.gmu.cs321;

import static org.junit.Assert.*;
import org.junit.Test;
class EmployeeTest {

    @Test
    void testGetandSetEmployeeID() {
        Employee employee = new Employee(0);
        employee.setEmployeeID(47);
        assertEquals(42, employee.getEmployeeID());
    }

    @Test
    void testReviewStatus() {
        Employee employee = new Employee(1);
        employee.setReviewStatus(true);
        assertTrue(employee.getReviewStatus());
    }
}
