package edu.gmu.cs321;
import static org.junit.Assert.*;

class DataEntryTest {

    @Test
    void testGetEntryID() {
        DataEntry data = new DataEntry(1, 11, 48);
        assertEquals(1, dataEntry.getEntryID());
    }

    @Test
    void testSetEntryID() {
        DataEntry dataEntry = new DataEntry(1, 23, 432);
        dataEntry.setEntryID(7);
        assertEquals(7, dataEntry.getEntryID());
    }

    @Test
    void testGetSponsorID() {
        DataEntry dataEntry = new DataEntry(3, 90, 101);
        assertEquals(90, dataEntry.getSponsorID());
    }

    @Test
    void testSetSponsorID() {
        DataEntry dataEntry = new DataEntry(4, 89, 532);
        dataEntry.setSponsorID(102);
        assertEquals(102, dataEntry.getSponsorID());
    }

    @Test
    void testGetFormID() {
        DataEntry dataEntry = new DataEntry(5, 15, 24);
        assertEquals(24, dataEntry.getFormID());
    }

    @Test
    void testSetFormID() {
        DataEntry dataEntry = new DataEntry(6, 32, 98);
        dataEntry.setFormID(100);
        assertEquals(100, dataEntry.getFormID());
    }

    @Test
    void testPerformDataEntry() {
        Sponsor sponsor = new Sponsor("Abdul Hussain", "abdul@example.com", 101, false);
        Form form = new Form(3, new String[]{"Zia"});
        DataEntry dataEntry = new DataEntry(1, 2, 3);
        dataEntry.performDataEntry(sponsor, form);
        assertTrue(form.isSubmitted());
    }
}
