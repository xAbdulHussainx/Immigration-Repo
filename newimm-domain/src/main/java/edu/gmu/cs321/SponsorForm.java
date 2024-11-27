package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.Random;
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

        // Sponsor income.
        Label sponsorIncome_Label = new Label("Sponsor income:");
        TextField sponsorIncome_Field = new TextField();
        sponsorIncome_Field.setPromptText("Enter income (Ex. $100,000)");
        
        grid.add(sponsorIncome_Label, 0, 13);
        grid.add(sponsorIncome_Field, 1, 13);

        // Agreement Checkbox
        CheckBox agreementCheckbox = new CheckBox("I agree that the information provided is accurate.");
        grid.add(agreementCheckbox, 1, 14);

        // Create an HBox for the buttons
        HBox buttonBox = new HBox(10); // Add spacing between buttons
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT); // Align buttons to the bottom-right

        // Submit Button
        Button submitButton = new Button("SUBMIT");

        // Label for error message (initially empty)
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);  // Set text color to red
        grid.add(errorMessage, 1, 16);  // Adding the error message below the submit button

        // Cancel Button
        Button cancelButton = new Button("CANCEL");
        buttonBox.getChildren().addAll(cancelButton, submitButton); // Add buttons in order
        grid.add(buttonBox, 1, 15); // Place the button box in the last row

        cancelButton.setOnAction(event -> {
            // Confirmation dialog before canceling
            Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
            cancelAlert.setTitle("Cancel Form");
            cancelAlert.setHeaderText("Are you sure you want to cancel?");
            cancelAlert.setContentText("All entered data will be lost.");

            cancelAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Redirect to ImmigrationSponsorshipLogin
                        ImmigrationSponsorshipLogin loginScreen = new ImmigrationSponsorshipLogin();
                        Stage loginStage = new Stage();

                        // Start the login menu
                        loginScreen.start(loginStage);

                        // Close the SponsorForm window
                        primaryStage.close();
                    } catch (Exception e) {
                        errorMessage.setText("Unable to return to login menu.");
                    }
                }
            });
        });

        submitButton.setOnAction(event -> {
            if (validateForm(nameField, DOB_pick, genderComboBox, nationalityField, phoneField, emailField, relationshipField, sponsorIncome_Field, agreementCheckbox, addressArea, sponsorshipReasonArea)) {
                
                // Show confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Submission");
                confirmationAlert.setHeaderText("Are you sure you want to submit?");
                confirmationAlert.setContentText("Please review your details before submitting.");

                // Wait for the user's response
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // generating unique ID.
                        Random random = new Random();
                        int num = 100000 + random.nextInt(900000);
                        String unique_id = String.valueOf(num);

                        // Retrieve form data
                        String fullName = nameField.getText();
                        Date dob = Date.valueOf(DOB_pick.getValue());
                        String gender = genderComboBox.getValue();
                        String nationality = nationalityField.getText();
                        String phone = phoneField.getText();
                        String email = emailField.getText();
                        String relationship = relationshipField.getText();
                        String caseType = caseTypeComboBox.getValue();
                        String sponsorshipReason = sponsorshipReasonArea.getText();
                        String applicantName = applicantNameField.getText();
                        Date applicantDob = Date.valueOf(applicantDobPicker.getValue());
                        String intendedStay = stayField.getText();
                        String sponsorIncome = sponsorIncome_Field.getText();
                        boolean isAgreed = agreementCheckbox.isSelected();
                        String address = addressArea.getText();

                        int form_status = TestDatabase.createSponsor(fullName, dob, gender, nationality, phone, email,
                                relationship, caseType, sponsorshipReason, applicantName, applicantDob, intendedStay,
                                unique_id, sponsorIncome, isAgreed, address);

                        // Create a new grid for the success message
                        GridPane successGrid = new GridPane();
                        successGrid.setPadding(new Insets(10));
                        successGrid.setHgap(10);
                        successGrid.setVgap(13);

                        if (form_status > 0) {

                            // Success message
                            Label successMessage = new Label(
                                    "Form submitted successfully!\n" + "  " + "Your unique ID is: " + unique_id);
                            successGrid.add(successMessage, 0, 0);
                            successMessage.setTextFill(Color.GREEN);

                            // Center the success message and back button
                            successGrid.setAlignment(Pos.CENTER);

                            // Back to main menu button
                            Button backButton = new Button("Go Back to Main Menu");
                            successGrid.add(backButton, 0, 1);

                            // Action for back button
                            backButton.setOnAction(backEvent -> {
                                try {
                                    // Create a new EmployeeScreen instance
                                    ImmigrationSponsorshipLogin loginScreen = new ImmigrationSponsorshipLogin();
                                    Stage loginStage = new Stage();

                                    // Start the EmployeeScreen
                                    loginScreen.start(loginStage);

                                    // Close the current SponsorForm window
                                    primaryStage.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    errorMessage.setText("Unable to open login Screen.");
                                }
                            });

                            // Update the scene to show the success message
                            primaryStage.setScene(new Scene(successGrid, 650, 200)); // Adjust height as needed

                        } else if (form_status <= 0) {
                            errorMessage.setText("Could not create sponsor.");
                        }
                    } else if (response == ButtonType.CANCEL) {
                        // User canceled submission - Do nothing and stay on the sponsor form
                        System.out.println("Submission canceled. Returning to the form for editing.");
                    }
                });
            } else {
                errorMessage.setText("Please complete all required fields correctly.");
            }
        });

        // Create scene and show stage
        Scene scene = new Scene(grid, 650, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Form Validation Method
    private boolean validateForm(TextField nameField, DatePicker dobPicker, ComboBox<String> genderComboBox, TextField nationalityField, TextField phoneField, TextField emailField, TextField relationshipField, TextField sponsorIncome_Field, CheckBox agreementCheckbox, TextArea addressArea, TextArea sponsorshipReasonArea) {
        boolean Valid = true;
        
        // Check if required fields are filled
        // For now we are only checking if the fields are filled, since most of the checking and validation will be done by an immigration officer.
        if (nameField.getText().isEmpty() || dobPicker.getValue() == null || genderComboBox.getValue() == null || nationalityField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty() || relationshipField.getText().isEmpty() || relationshipField.getText().isEmpty() || !agreementCheckbox.isSelected()|| addressArea.getText().isEmpty()|| sponsorshipReasonArea.getText().isEmpty()) {
            Valid = false;
        }

        return Valid;
    }

}

