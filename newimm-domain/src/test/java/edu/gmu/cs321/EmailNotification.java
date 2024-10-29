package edu.gmu.cs321;

class EmailNotificationTest {

    @Test
    void testConstructorAndGetEmailContent() {
        EmailNotification emailNotification = new EmailNotification("Some email notification");
        assertEquals("Some email notification", emailNotification.getEmailContent());
    }

    @Test
    void testSetEmailContent() {
        EmailNotification emailNotification = new EmailNotification("random");
        emailNotification.setEmailContent("form approved");
        assertEquals("form approved", emailNotification.getEmailContent());
    }

    @Test
    void testSendEmail() {
        Sponsor sponsor = new Sponsor("Somebody", "Somebody@example.com", 4, true);
        EmailNotification emailNotification = new EmailNotification("test email");
        emailNotification.sendEmail(sponsor);
        assertEquals("test email", sponsor.getEmail());
    }
}
