package edu.gmu.cs321;

class DatabaseHandlerTest {

    @Test
    void testSaveForm() {
        DatabaseHandler data = new DatabaseHandler();
        Form form = new Form(34, new String[]{"Abdul", "ZIA"});
        data.saveData(form);
    }

    @Test
    void testUpdateData() {
        DatabaseHandler data = new DatabaseHandler();
        Form form = new Form(12, new String[]{"monkey"});
        data.updateData(form);
    }

    @Test
    void testRetrieveData() { // I think this test needs work on. Haven't dealt with database in a long time. 
        DatabaseHandler data = new DatabaseHandler();
        Form Form = data.retrieveData();
        assertEquals(1, Form.getFormID());
    }
}
