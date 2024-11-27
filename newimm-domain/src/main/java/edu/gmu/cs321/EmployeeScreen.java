package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EmployeeScreen extends Application {

    private Stage window;
    private String selectedApplicationId = null;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        showEmployeeDashboard();
    }

    private void showEmployeeDashboard() {
        window.setTitle("Employee Dashboard");

        Label titleLabel = new Label("Employee Dashboard");
        titleLabel.setFont(Font.font(null, FontWeight.BOLD, 24));

        Button reviewFormButton = new Button("Review Sponsor Form");
        Button workflowTableButton = new Button("Workflow Table");

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(Font.font(null, FontWeight.BOLD, 16));

        logoutButton.setOnAction(e -> showLoginScreen());

        reviewFormButton.setFont(Font.font(null, FontWeight.BOLD, 16));
        workflowTableButton.setFont(Font.font(null, FontWeight.BOLD, 16));

        reviewFormButton.setOnAction(e -> showApplicationSearchScreen());
        workflowTableButton.setOnAction(e -> workflowTableScreen());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(titleLabel, reviewFormButton, workflowTableButton, logoutButton);

        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    private void showSponsorApplication(String applicationId) {
        window.setTitle("Sponsor Application Details");
    
        Label titleLabel = new Label("Sponsor Application Details");
        titleLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
    
        StringBuilder details = new StringBuilder();
        TestDatabase db = new TestDatabase();
        boolean found = db.fetchApplicationDetailsById(applicationId, details);
    
        if (found) {
            Label detailsLabel = new Label(details.toString());
            detailsLabel.setFont(Font.font(null, FontWeight.NORMAL, 14));
            detailsLabel.setWrapText(true);
    
            Button goBackButton = new Button("Go Back <--");
            goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
            goBackButton.setOnAction(e -> workflowTableScreen());
    
            VBox layout = new VBox(20);
            layout.setPadding(new Insets(30));
            layout.getChildren().addAll(titleLabel, detailsLabel, goBackButton);
    
            Scene detailsScene = new Scene(layout, 600, 600);
            window.setScene(detailsScene);
        } else {
            showAlert("Error", "Application ID not found.");
            workflowTableScreen();
        }
    }
    
    private void showApplicationSearchScreen() {
        window.setTitle("Application Search");
    
        Label searchLabel = new Label("Application Search");
        searchLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
    
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Application ID");
    
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font(null, FontWeight.BOLD, 14));
    
        VBox searchResult = new VBox(15);
        searchResult.setPadding(new Insets(10));
    
        submitButton.setOnAction(e -> {
            String enteredId = searchField.getText().trim();
            if (enteredId.isEmpty()) {
                showAlert("Error", "Please enter an Application ID.");
                return;
            }
            StringBuilder details = new StringBuilder();
    
            if (TestDatabase.fetchAllFieldsById(enteredId, details)) {

                Label detailsLabel = new Label(details.toString());
                detailsLabel.setFont(Font.font(null, FontWeight.NORMAL, 14));
                detailsLabel.setWrapText(true);
    
                searchResult.getChildren().clear();
                searchResult.getChildren().add(detailsLabel);
    
                Button sendUpdatesButton = new Button("Send Updates");
                sendUpdatesButton.setFont(Font.font(null, FontWeight.BOLD, 14));
                sendUpdatesButton.setOnAction(e2 -> showSendUpdateScreen(enteredId));
    
                Button approveButton = new Button("Approve");
                approveButton.setFont(Font.font(null, FontWeight.BOLD, 14));
                approveButton.setOnAction(e2 -> {
                    updateApplicationStatus(enteredId, "Approved");
                    showEmployeeDashboard();
                    showAlert("Success", "Application successfully approved.");
                });
    
                Button denyButton = new Button("Deny");
                denyButton.setFont(Font.font(null, FontWeight.BOLD, 14));
                denyButton.setOnAction(e2 -> {
                    updateApplicationStatus(enteredId, "Denied");
                    showEmployeeDashboard();
                    showAlert("Success", "Application successfully denied.");
                });
    
                searchResult.getChildren().addAll(sendUpdatesButton, approveButton, denyButton);
            } else {
                showAlert("Error", "No application found with ID: " + enteredId);
            }
        });
    
        Button goBackButton = new Button("Go Back <--");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> showEmployeeDashboard());
    
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(searchLabel, searchField, submitButton, searchResult, goBackButton);
    
        Scene searchScene = new Scene(layout, 500, 400);
        window.setScene(searchScene);
    }
    
    private void showLoginScreen() {
        ImmigrationSponsorshipLogin loginScreen = new ImmigrationSponsorshipLogin();
        loginScreen.start(window);
    }

    private void workflowTableScreen() {
        window.setTitle("Workflow Table");

        Label workflowLabel = new Label("Workflow Table");
        workflowLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        CheckBox needsReviewFilter = new CheckBox("Denied");
        CheckBox inProgressFilter = new CheckBox("In Progress");
        CheckBox approvedFilter = new CheckBox("Approved");

        VBox applicationList = new VBox(15);
        applicationList.setPadding(new Insets(10));

        TestDatabase db = new TestDatabase();
        List<String[]> applications = new ArrayList<>();

        boolean dataFetched = db.fetchApplicationsWithStatus(applications);
        if (!dataFetched) {
            applicationList.getChildren().add(new Label("No applications found or database connection failed."));
        } else {
            updateApplicationList(applications, applicationList, null);
        }

        Button applyFiltersButton = new Button("Apply Filters");
        applyFiltersButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        applyFiltersButton.setOnAction(e -> applyFilters(db, applicationList, needsReviewFilter, inProgressFilter, approvedFilter));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(applicationList);
        scrollPane.setFitToWidth(true);

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        HBox filterBox = new HBox(15, needsReviewFilter, inProgressFilter, approvedFilter);
        filterBox.setPadding(new Insets(10));

        VBox layout = new VBox(20, workflowLabel, filterBox, applyFiltersButton, scrollPane, goBackButton);
        layout.setPadding(new Insets(30));

        Scene workflowScene = new Scene(layout, 600, 500);
        window.setScene(workflowScene);
    }

    private void applyFilters(TestDatabase db, VBox applicationList, CheckBox needsReview, CheckBox inProgress, CheckBox approved) {
        String selectedStatus = null;

        if (needsReview.isSelected() && !inProgress.isSelected() && !approved.isSelected()) {
            selectedStatus = "Denied";
        } else if (!needsReview.isSelected() && inProgress.isSelected() && !approved.isSelected()) {
            selectedStatus = "in-progress";
        } else if (!needsReview.isSelected() && !inProgress.isSelected() && approved.isSelected()) {
            selectedStatus = "approved";
        }

        List<String[]> applications = new ArrayList<>();
        if (db.fetchApplicationsWithStatus(applications)) {
            updateApplicationList(applications, applicationList, selectedStatus);
        }
    }

    private void updateApplicationList(List<String[]> applications, VBox applicationList, String statusFilter) {
        applicationList.getChildren().clear();

        for (String[] app : applications) {
            String appId = app[0];
            String appStatus = app[1];

            if (statusFilter == null || appStatus.equalsIgnoreCase(statusFilter)) {
                HBox applicationRow = new HBox(15);
                Label applicationLabel = new Label("ID: " + appId + " | Status: " + appStatus);

                Button viewButton = new Button("View");
                viewButton.setOnAction(e -> showSponsorApplication(appId));

                applicationRow.getChildren().addAll(applicationLabel, viewButton);
                applicationList.getChildren().add(applicationRow);
            }
        }

        if (applicationList.getChildren().isEmpty()) {
            applicationList.getChildren().add(new Label("No applications match the selected filter."));
        }
    }

    private void showSendUpdateScreen(String applicationId) {
        window.setTitle("Send Application Update");

        Label updateLabel = new Label("Send Application Update");
        updateLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        TextArea updateTextArea = new TextArea();
        updateTextArea.setPromptText("Enter update message here...");
        updateTextArea.setPrefHeight(100);

        Label appIdLabel = new Label("Application ID: " + applicationId);
        appIdLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        submitButton.setOnAction(e -> {
            String updateMessage = updateTextArea.getText().trim();

            if (updateMessage.isEmpty()) {
                showAlert("Error", "Please enter an update message.");
                return;
            }

            TestDatabase db = new TestDatabase();
            db.updateApplicationStatus(applicationId, applicationId);
            showSuccessScreen();
        });

        Button goBackButton = new Button("Go Back <--");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> workflowTableScreen());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(updateLabel, appIdLabel, updateTextArea, submitButton, goBackButton);

        Scene updateScene = new Scene(layout, 500, 500);
        window.setScene(updateScene);
    }

    private void updateApplicationStatus(String applicationId, String status) {
        TestDatabase db = new TestDatabase();
        db.updateApplicationStatus(applicationId, status);
        showSuccessScreen();
    }

    private void showSuccessScreen() {
        window.setTitle("Success");

        Label successLabel = new Label("Action Submitted Successfully!");
        successLabel.setFont(Font.font(null, FontWeight.BOLD, 18));

        Button goBackButton = new Button("Go Back to Employee Dashboard");
        goBackButton.setFont(Font.font(null, FontWeight.BOLD, 14));
        goBackButton.setOnAction(e -> showEmployeeDashboard());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(successLabel, goBackButton);

        Scene successScene = new Scene(layout, 500, 300);
        window.setScene(successScene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
