package edu.gmu.cs321;
import static org.junit.Assert.*;

import org.junit.Test;

class FormTest {

    @Test
    void testFormConstructorAndGetters() {
        Form form = new Form(4, new String[]{"ZIA", "ARSH"});
        assertEquals(4, form.getFormID());
        assertArrayEquals(new String[]{"ZIA", "ARSH"}, form.getDataFields());
    }

    @Test
    void testSetDataFields() {
        Form form = new Form(0, new String[]{});
        form.setDataFields(new String[]{"ZIA", "Arsh"});
        assertArrayEquals(new String[]{"ZIA", "Arsh"}, form.getDataFields());
    }

    @Test
    void testSubmitForm() {
        Form form = new Form(1, new String[]{"Abdul"});
        Sponsor sponsor = new Sponsor("ZIA", "ZIA@example.com", 101);
        form.submit(sponsor);
        assertTrue(form.isSubmitted());
    }

    @Test
    void testValidateForm() {
        Form form = new Form(3, new String[]{"abcd", "dcba"});
        Validation validation = new Validation();
        form.validateForm(validation);
        assertTrue(form.getValidationStatus()); 
    }
    
}
