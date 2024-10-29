package edu.gmu.cs321;
import static org.junit.Assert.*;

import org.junit.Test;

class ValidationTest {

    @Test
    void testValidationStatus() {
        Validation validation = new Validation();
        validation.setValidationStatus(true);
        assertTrue(validation.getValidationStatus());
    }

    @Test
    void testRunValidations() {
        Validation validation = new Validation();
        Form form = new Form(1, new String[]{"field1"});
        assertTrue(validation.runValidations(form));
    }

    @Test
    void testCheckSponsorExits() {
        Validation validation = new Validation();
        Sponsor sponsor = new Sponsor("Some Sponsor", "sponsor@example.com", 324);
        assertTrue(validation.checkSponsorExits(324));
    }
}