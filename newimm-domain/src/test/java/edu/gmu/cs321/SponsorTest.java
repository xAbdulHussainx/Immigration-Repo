package edu.gmu.cs321;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SponsorTest {
    
    @Test
    void testConstructorAndGetters() {
        Sponsor sponsor = new Sponsor("Ryan Renolds", "Ryan@example.com", 1);
        assertEquals("Ryan Renolds", sponsor.getName());
        assertEquals("Ryan@example.com", sponsor.getEmail());
        assertEquals(1, sponsor.getSponsorID());
        assertTrue(sponsor.isActive());
    }

    @Test
    void testSetName() {
        Sponsor sponsor = new Sponsor("Adam", "Adam@example.com", 2, true);
        sponsor.setName("NotAdam");
        assertEquals("NotAdam", sponsor.getName());
    }

    @Test
    void testSetEmail() {
        Sponsor sponsor = new Sponsor("Sandler", "Sandler@example.com", 3, true);
        sponsor.setEmail("definitelyNotSandler@example.com");
        assertEquals("definitelyNotSandler@example.com", sponsor.getEmail());
    }

    @Test
    void testSubmitForm() {
        Sponsor sponsor = new Sponsor("Somebody", "Somebody@example.com", 4, true);
        Form form = new Form(1, new String[]{"something", "something2"});
        sponsor.submitForm(form);
        assertTrue(form.isSubmitted());
    }

    @Test
    void testReceiveEmail() {
        Sponsor sponsor = new Sponsor("Messi", "Messi@example.com", 9, true);
        sponsor.receiveEmail("testing email");
    }
}
