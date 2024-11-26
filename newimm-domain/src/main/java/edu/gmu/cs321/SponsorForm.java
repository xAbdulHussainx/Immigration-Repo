package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.regex.Pattern;

public class SponsorForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sponsor Form");

        // Create the grid layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); 
        grid.setHgap(10);  
        grid.setVgap(13); 

        // Name entry Data feild.
        Label Name = new Label("Full Name:"); 
        TextField nameField = new TextField(); 
        nameField.setPromptText("Enter full name");

        grid.add(Name, 0, 0);
        grid.add(nameField, 1, 0);

        // Date of Birth Field
        Label DOB = new Label("Date of Birth :");
        DatePicker DOB_pick = new DatePicker();

        grid.add(DOB, 0, 1);
        grid.add(DOB_pick, 1, 1);

        // Gender Field
        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");

        grid.add(genderLabel, 0, 2);
        grid.add(genderComboBox, 1, 2);

        // Nationality Field
        Label nationalityLabel = new Label("Sponsor Residency:");
        TextField nationalityField = new TextField();
        nationalityField.setPromptText("Enter your country of residence");

        grid.add(nationalityLabel, 0, 3);
        grid.add(nationalityField, 1, 3);

        // Address Field
        Label addressLabel = new Label("Sponsor Address:");
        TextArea addressArea = new TextArea();
        addressArea.setPromptText("Enter address");
        addressArea.setPrefRowCount(2);

        grid.add(addressLabel, 0, 4);
        grid.add(addressArea, 1, 4);

        // Phone Number Field with Validation
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");

        grid.add(phoneLabel, 0, 5);
        grid.add(phoneField, 1, 5);

        // Only allowing the iput of numbers in the phone number feild.
        phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (Pattern.matches("\\d*", newVal) == false) {
                phoneField.setText(oldVal); 
            }
        });

        // Email Address Field with Validation
        Label emailLabel = new Label("Email Address:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");

        grid.add(emailLabel, 0, 6);
        grid.add(emailField, 1, 6);


        // Relationship to Applicant Field
        Label relationshipLabel = new Label("Relation to Applicant:");
        TextField relationshipField = new TextField();
        relationshipField.setPromptText("Enter relationship (Ex. Mother, Father,..etc)");
        
        grid.add(relationshipLabel, 0, 7);
        grid.add(relationshipField, 1, 7);

        // Case Type Field
        Label caseTypeLabel = new Label("Case Type:");
        ComboBox<String> caseTypeComboBox = new ComboBox<>();
        caseTypeComboBox.getItems().addAll("Family-based", "Employment-based", "Refugee", "Student");

        grid.add(caseTypeLabel, 0, 8);
        grid.add(caseTypeComboBox, 1, 8);

        // Sponsorship Reason Field
        Label sponsorshipReasonLabel = new Label("Sponsorship Reason:");
        TextArea sponsorshipReasonArea = new TextArea();
        sponsorshipReasonArea.setPromptText("Describe reason for sponsorship");
        sponsorshipReasonArea.setPrefRowCount(2);

        grid.add(sponsorshipReasonLabel, 0, 9);
        grid.add(sponsorshipReasonArea, 1, 9);

        // Applicant Name Field
        Label applicantNameLabel = new Label("Applicant Name:");
        TextField applicantNameField = new TextField();
        applicantNameField.setPromptText("Enter applicant's full name");

        grid.add(applicantNameLabel, 0, 10);
        grid.add(applicantNameField, 1, 10);

        // Applicant Date of Birth Field
        Label applicantDobLabel = new Label("Applicant Date of Birth:");
        DatePicker applicantDobPicker = new DatePicker();

        grid.add(applicantDobLabel, 0, 11);
        grid.add(applicantDobPicker, 1, 11);

        // Intended Length of Stay Field
        Label stayLabel = new Label("Intended Length of Stay:");
        TextField stayField = new TextField();
        stayField.setPromptText("Ex. 6 months, 1 year");

        grid.add(stayLabel, 0, 12);
        grid.add(stayField, 1, 12);

        // For now we only added a checkbox but we still need a method to upload a file or an image.
        CheckBox financialSupportCheckbox = new CheckBox("Proof of Financial Support Provided");
        grid.add(financialSupportCheckbox, 1, 13);

        // Agreement Checkbox
        CheckBox agreementCheckbox = new CheckBox("I agree that the information provided is accurate.");
        grid.add(agreementCheckbox, 1, 14);

        // Submit Button
        Button submitButton = new Button("SUBMIT");
        grid.add(submitButton, 1, 15);

        submitButton.setOnAction(event -> {
            if (validateForm(nameField, DOB_pick, genderComboBox, nationalityField, phoneField, emailField, relationshipField, caseTypeComboBox, agreementCheckbox)) {
                // Presently we are not processing the inputed data. Moving forward we intend to integrate a database that will store all this data.
                System.out.println("Form submitted successfully!");
            } else {
                System.out.println("Please complete all required fields correctly.");
            }
        });

        // Create scene and show stage
        Scene scene = new Scene(grid, 650, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Form Validation Method
    private boolean validateForm(TextField nameField, DatePicker dobPicker, ComboBox<String> genderComboBox, TextField nationalityField, TextField phoneField, TextField emailField, TextField relationshipField, ComboBox<String> caseTypeComboBox, CheckBox agreementCheckbox) {
        boolean Valid = true;
        
        // Check if required fields are filled
        // For now we are only checking if the fields are filled, since most of the checking and validation will be done by an immigration officer.
        if (nameField.getText().isEmpty() || dobPicker.getValue() == null || genderComboBox.getValue() == null || nationalityField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty() || relationshipField.getText().isEmpty() || caseTypeComboBox.getValue() == null || !agreementCheckbox.isSelected()) {
            Valid = false;
        }

        return Valid;
    }

}

